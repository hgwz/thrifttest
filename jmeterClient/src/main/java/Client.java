
import com.testerhome.service.ComputeServer;
import com.testerhome.service.ComputeRequest;
import com.testerhome.service.ComputeResponse;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TFramedTransport;

public class Client {
    private ComputeServer.Client client = null;
    private TTransport transport = null;

    protected Client(String ip, int port) {
        try {
            TTransport transport = new TFramedTransport(new TSocket(ip, port));
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new ComputeServer.Client(protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ComputeResponse getResponse(ComputeRequest request) {
        try {
            ComputeResponse response = client.getComputeResult(request);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void close() {
        if (transport != null && transport.isOpen()) {
            transport.close();
        }
    }
}