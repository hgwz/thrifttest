import com.testerhome.service.ComputeServer;
import com.testerhome.service.ComputeRequest;
import com.testerhome.service.ComputeResponse;
import com.testerhome.service.ComputeType;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Impl implements ComputeServer.Iface {
    private static final Logger logger = LogManager.getLogger(Impl.class);

    public ComputeResponse getComputeResult(ComputeRequest request) {
        ComputeType computeType = request.getComputeType();
        long x = request.getX();
        long y = request.getY();
        logger.info("Compute result begin. [x:{}] [y:{}] [type:{}]", x, y, computeType.toString());
        long begin = System.currentTimeMillis();

        ComputeResponse response = new ComputeResponse();
        response.setErrorNo(0);
        try {
            long ret;
            if (computeType == ComputeType.ADD) {
                ret = add(x, y);
                response.setComputeRet(ret);
            } else {
                ret = sub(x, y);
                response.setComputeRet(ret);
            }
        } catch (Exception e) {
            response.setErrorNo(1001);
            response.setErrorMsg(e.getMessage());
            logger.error("Exception:", e);
        }

        long end = System.currentTimeMillis();
        logger.info("Compute result end. [errno:{}] cost:[{}ms]",
                response.getErrorNo(), (end - begin));
        return response;
    }

    public long add(long x, long y) {
        return x + y;
    }
    public long sub(long x, long y) {
        return x - y;
    }
}
