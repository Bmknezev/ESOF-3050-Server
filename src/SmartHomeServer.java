import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import smartDevice.*;
import java.io.IOException;
import java.util.List;


public class SmartHomeServer extends AbstractServer {
    List<Object> devices = new java.util.ArrayList<>();
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
        //message format:    send/receive@deviceID@message(if receive, delimit with |)
        String[] s = msg.toString().split("@");
        if(Boolean.parseBoolean(s[0])){
            //send
            sendToDevice(s[1], client);
        }
        else{
            //receive
            receiveFromDevice(s[1], s[2]);
        }
    }

    private void receiveFromDevice(String s, String s1) {
        System.out.println(s1);
        String[] updates = s1.split("\\|");
        //get device from list
        Object device = devices.get(Integer.parseInt(s)-1);
        //update device
        ((SmartDevice)device).update(updates);
        System.out.println("Updated device: " + device);

    }

    private void sendToDevice(String s, ConnectionToClient client) {
        System.out.println("Sending to device: " + s);
        if(s.equals("-1")){
            //send list of devices
            String message = "1@";
            for (Object device : devices) {
                message += device.toString() + "~";
            }
            try {
                client.sendToClient(message);
            } catch (IOException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
        }else {
            try {
                //get device from list
                Object device = devices.get(Integer.parseInt(s) - 1);
                client.sendToClient(0+"@"+((SmartDevice) device).getDetails());
            } catch (IOException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
        }
    }

    public void newDevice(Object device){
        devices.add(device);
    }

}
