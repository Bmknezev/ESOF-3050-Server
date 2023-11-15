import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import messages.AbstractDeviceMessage;
import messages.AbstractMessage;
import messages.NewDeviceMessage;
import smartDevice.*;
import java.io.IOException;
import java.util.List;


public class SmartHomeServer extends AbstractServer {
    List<SmartDevice> devices = new java.util.ArrayList<>();
    List<ConnectionToClient> clientList = new java.util.ArrayList<>();
    List<Integer> clientIDList = new java.util.ArrayList<>();
    private int totalClients = 0;
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public SmartHomeServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client){
        System.out.println("Message received: " + msg.toString());
        //check message type

        if(((AbstractMessage)msg).getStartup()){
            //if message is client startup message
            //add client to list
            clientList.add(client);
            //add clientID to list
            clientIDList.add(totalClients);
            //send clientID to client
            try {
                client.sendToClient(totalClients);
            } catch (IOException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
            //send list of devices to client
            for (SmartDevice device : devices) {
                try {
                    NewDeviceMessage message = new NewDeviceMessage(true, device.getDeviceID(), device.getName(),device.getType());
                    client.sendToClient(message);
                } catch (IOException e) {
                    System.out.println("Error sending message to client.");
                    throw new RuntimeException(e);
                }
            }

        }else if(((AbstractMessage)msg).getMessageType()){
            //if message is request
            sendToDevice((AbstractDeviceMessage)msg, client);
        }else{
            //if message is response
            receiveFromDevice((AbstractDeviceMessage)msg);
        }


        //((SmartDevice) devices.get(((AbstractMessage)msg).getDeviceID()-1)).update((AbstractMessage)msg);
    }

    private void receiveFromDevice(AbstractDeviceMessage msg) {
        //get device from list
        SmartDevice device = devices.get(msg.getDeviceID()-1);
        //update device
        device.update(msg);

    }

    private void sendToDevice(AbstractDeviceMessage msg, ConnectionToClient client) {
        //get deviceID from message and get device from list
        SmartDevice device = devices.get(msg.getDeviceID()-1);
            try {
                client.sendToClient(device.PrepareMessage());
            } catch (IOException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
        }

    public void newDevice(SmartDevice device){
        devices.add(device);
    }

}
