import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import messages.*;
import messages.automations.AbstractAutomationMessage;
import smartDevice.*;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

import static java.lang.Thread.sleep;


public class SmartHomeServer extends AbstractServer {
    List<SmartDevice> devices = new java.util.ArrayList<>();
    List<ConnectionToClient> clientList = new java.util.ArrayList<>();
    List<Integer> clientIDList = new java.util.ArrayList<>();
    private int totalClients = 0;
    Timer timer = new Timer();

    List<String> usernames = new java.util.ArrayList<>();
    List<String> passwords = new java.util.ArrayList<>();
    
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public SmartHomeServer(int port) {
        super(port);

        //add usernames and passwords
        usernames.add("admin");
        passwords.add("admin");
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client){
        System.out.println("Message received: " + msg.toString());
        //check message type

        switch (((AbstractMessage)msg).getType()){
            case 1:
                //client is requesting device details
                updateDeviceDetails((AbstractDeviceMessage)msg, client);
                break;
            case 2:
                //client is sending device details
                sendDetails((NewDeviceMessage)msg, client);
                break;
            case 3:
                //new client connects
                totalClients++;
                clientList.add(client);
                clientIDList.add(totalClients);
                StartupMessage message = new StartupMessage(totalClients);
                try {
                    client.sendToClient(message);
                } catch (IOException e) {
                    System.out.println("Error sending message to client.");
                    throw new RuntimeException(e);
                }
                SendDevices(client);
                break;
            case 4:
                //client is sending automation message
                System.out.println("Automation message received.");
                DeviceAutomation((AbstractAutomationMessage)msg);
                break;
                case 5:
                //client is sending login details
                System.out.println("Login details received.");
                Login((LoginMessage)msg, client);
        }
    }

    private void Login(LoginMessage msg, ConnectionToClient client) {
        System.out.println("Login details received.");
        //check if username and password are correct
        for(int i = 0; i < usernames.size(); i++){
            if(usernames.get(i).equals(msg.getUsername()) && passwords.get(i).equals(msg.getPassword())) {
                System.out.println("Login successful.");
                //send success message
                try {
                    msg.setLoginStatus(true);
                    client.sendToClient(msg);
                } catch (IOException e) {
                    System.out.println("Error sending message to client.");
                    throw new RuntimeException(e);
                }
                return;
            }
                //send fail message
                try {
                    System.out.println("Login failed.");
                    msg.setLoginStatus(false);
                    client.sendToClient(msg);
                } catch (IOException e) {
                    System.out.println("Error sending message to client.");
                    throw new RuntimeException(e);
                }

        }
    }

    private void DeviceAutomation(AbstractAutomationMessage msg) {
        //get device from list
        SmartDevice device = devices.get(msg.getDeviceID()-1);
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                device.Automation(msg);
            }
        }, msg.getDate());

    }

    private void SendDevices(ConnectionToClient client) {
        for (SmartDevice device : devices) {
            try {
                sleep(100);
                NewDeviceMessage msg = new NewDeviceMessage(device.getDeviceID(), device.getName(), device.getType());
                client.sendToClient(msg);
            } catch (IOException | InterruptedException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
        }
    }

    private void updateDeviceDetails(AbstractDeviceMessage msg, ConnectionToClient client) {
        System.out.println("Device details received." + msg.getDeviceID());
        //get device from list
        SmartDevice device = devices.get(msg.getDeviceID()-1);
        //update device
        device.update(msg);
        //send device to client
        try {
            client.sendToClient(device.PrepareMessage());
        } catch (IOException e) {
            System.out.println("Error sending message to client.");
            throw new RuntimeException(e);
        }
    }

    private void sendDetails(NewDeviceMessage msg, ConnectionToClient client) {
        //get deviceID from message and get device from list
        SmartDevice device = devices.get((msg).getDeviceID()-1);
        System.out.println("Sending details for device " + device.getName() + " to client " + clientIDList.get(clientList.indexOf(client)));
            try {
                client.sendToClient(device.PrepareMessage());
                System.out.println("Message sent: " + device.PrepareMessage().toString());
            } catch (IOException e) {
                System.out.println("Error sending message to client.");
                throw new RuntimeException(e);
            }
        }

    public void newDevice(List<SmartDevice> device){
        devices = device;
    }

}
