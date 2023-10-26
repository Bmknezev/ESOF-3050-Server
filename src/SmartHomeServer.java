import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import java.io.IOException;

public class SmartHomeServer extends AbstractServer {

    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public SmartHomeServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("recived message from client");
        try {
            this.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
