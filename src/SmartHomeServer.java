import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.Objects;

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
        String message = msg.toString();
        if(!Objects.equals(message, "/close")){
            System.out.println(message);
        }else{
          System.out.println("client disconnected");
                try {
                    this.stopListening();
                    this.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);

            }
        }
    }
}
