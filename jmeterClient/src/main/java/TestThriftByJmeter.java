import com.testerhome.service.ComputeRequest;
import com.testerhome.service.ComputeResponse;
import com.testerhome.service.ComputeType;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class TestThriftByJmeter extends AbstractJavaSamplerClient {
    private Client client;
    private ComputeRequest request;
    private ComputeResponse response;

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("ip", "127.0.0.1");
        arguments.addArgument("port", "9000");

        arguments.addArgument("X", "0");
        arguments.addArgument("Y", "0");
        arguments.addArgument("type", "0");
        return arguments;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        try {
            // 获取参数
            String ip = context.getParameter("ip");
            int port = context.getIntParameter("port");
            int x = context.getIntParameter("X");
            int y = context.getIntParameter("Y");
            ComputeType type = getComputeType(context.getIntParameter("type"));

            // 创建客户端
            client = new Client(ip, port);
            // 设置request请求
            request = new ComputeRequest(x, y, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setupTest(context);
    }

    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.sampleStart();
        try {
            long begin = System. currentTimeMillis();
            response = client.getResponse(request);
            long cost = (System.currentTimeMillis() - begin);
            System.out.println(response.toString() + " cost:[" + cost + "ms]");

            if (response == null) {
                result.setSuccessful(false);
                return result;
            }
            if (response.getErrorNo() == 0) {
                result.setSuccessful(true);
                result.setResponseData(Long.toString(response.getComputeRet()), "utf-8");
            } else {
                result.setSuccessful(false);
                result.setResponseData(response.getErrorMsg(), "utf-8");
            }
        } catch (Exception e) {
            result.setSuccessful(false);
            e.printStackTrace();
        }  finally {
            result.sampleEnd();
        }
        return result;
    }

    public void tearDownTest(JavaSamplerContext context) {
        if (client != null) {
            client.close();
        }
        super.teardownTest(context);
    }

    ComputeType getComputeType(int type) {
        ComputeType computeType;
        switch (type) {
            case 0: computeType = ComputeType.ADD; break;
            default: computeType = ComputeType.ADD; break;
        }
        return computeType;
    }
}