import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;

public class SmartHomeServer extends AbstractServer {
    BlockingDeque<Object> q;
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public SmartHomeServer(int port, BlockingDeque<Object> q) {
        super(port);
        this.q = q;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg.toString() + " from " + client);

        try {
            q.put(msg);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
