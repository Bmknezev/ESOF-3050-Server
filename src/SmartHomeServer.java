import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class SmartHomeServer extends AbstractServer {
    BlockingDeque<Object> q;
    List<Object> devices = new java.util.ArrayList<>();
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

            int i = Integer.parseInt(msg.toString());
            System.out.println(devices.size());
            Object message = devices.get(i-1);
            client.sendToClient("incoming message");
            client.sendToClient(message);
            client.sendToClient("end of message");
            System.out.println("Message sent");

            //client.sendToClient(1);

        } catch (IOException e) {
            System.out.println("Error sending message to client.");
            throw new RuntimeException(e);
        }

    }

public void newDevice(Object device){
        devices.add(device);
}

}
