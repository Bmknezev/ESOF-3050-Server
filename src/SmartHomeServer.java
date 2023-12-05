//-----------------------------------------------------------------
// SmartHomeServer.java
// Group 2
// Description: This class represents the Smart Home Server managing various smart devices and client connections.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
// - devices: A list containing SmartDevice instances connected to the Smart Home Server.
// - clientList: A list storing active connections to clients.
// - clientIDList: A list keeping track of client IDs corresponding to connected clients.
// - totalClients: An integer representing the total number of connected clients.
// - timer: A timer for scheduling device automation tasks.
// - usernames: A list of usernames for user authentication.
// - passwords: A list of corresponding passwords associated with the usernames for authentication.
// - admin: A list of boolean values representing admin privileges corresponding to user accounts.
//
// Error Handling: The class includes error handling for sending and receiving messages to/from clients,
//                 handling device updates, and managing user-related operations such as logins and user lists.
//
// NOTE: This server class interacts with SmartDevice instances to manage their updates, automation, and client connections.
//       It includes functionalities for handling client connections, device updates, user authentication, and user management.
//-----------------------------------------------------------------


import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import messages.*;
import messages.automations.AbstractAutomationMessage;
import smartDevice.*;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

public class SmartHomeServer extends AbstractServer {
    List<SmartDevice> devices = new java.util.ArrayList<>();
    List<ConnectionToClient> clientList = new java.util.ArrayList<>();
    List<Integer> clientIDList = new java.util.ArrayList<>();
    private int totalClients = 0;
    Timer timer = new Timer(); //this is a timer

    List<String> usernames = new java.util.ArrayList<>();
    List<String> passwords = new java.util.ArrayList<>();
    List<Boolean> admin = new java.util.ArrayList<>();
    
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
        admin.add(true);

        usernames.add("user");
        passwords.add("user");
        admin.add(false);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client){
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
                SendDevices(client);
                break;
            case 4:
                //client is sending automation message
                //System.out.println("Automation message received.");
                DeviceAutomation((AbstractAutomationMessage)msg);
                break;
            case 5:
                //client is sending login details
                //System.out.println("Login details received.");
                Login((LoginMessage)msg, client);
                break;
            case 7:
                //client is requesting user list
                if(((UserListMessage)msg).getUserID() == -1){
                    //System.out.println("User list requested.");
                    SendUsers(client);
                    return;
                }
                if(((UserListMessage)msg).getNewUser())
                    AddUser((UserListMessage)msg, client);
                else
                    modifyUser((UserListMessage)msg, client);
                SendUsers(client);
                break;
        }
    }

    private void modifyUser(UserListMessage msg, ConnectionToClient client) {
        for(int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(msg.getUsername()) && i != msg.getUserID()) {
                System.out.println("User already exists.");
                send(new UserListMessage(-1,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                return;
            }
        }
        if(!msg.getAdmin()){
            int tmp = 0;
            for(int i = 0; i < admin.size(); i++) {
                if(admin.get(i))
                    tmp++;

            }
            if(tmp == 1 && admin.get(msg.getUserID())){
                send(new UserListMessage(-3,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                return;
            }

        }
        usernames.set(msg.getUserID(), msg.getUsername());
        passwords.set(msg.getUserID(), msg.getPassword());
        admin.set(msg.getUserID(), msg.getAdmin());
    }

    private void AddUser(UserListMessage msg, ConnectionToClient client) {
        //System.out.println("Adding user " + msg.getUsername());
        //check if user exists
        for(int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(msg.getUsername())) {
                System.out.println("User already exists.");
                send(new UserListMessage(-1,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                return;
            }
        }
            usernames.add(msg.getUsername());
            passwords.add(msg.getPassword());
            admin.add(msg.getAdmin());

    }

    private void SendUsers(ConnectionToClient client) {
        for(int i = 0; i < usernames.size(); i++) {
            UserListMessage msg = new UserListMessage(i, usernames.get(i), passwords.get(i), admin.get(i), true);
            send(msg, client);
        }
    }

    private void Login(LoginMessage msg, ConnectionToClient client) {
        //System.out.println("Login details received.");
        //check if username and password are correct
        for(int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(msg.getUsername()) && passwords.get(i).equals(msg.getPassword())) {
                //System.out.println("Login successful.");
                msg.setLoginStatus(true);
                msg.setAdmin(admin.get(i));
                //send success message
                send(msg, client);
                return;
            }
        }
        //System.out.println("Login failed.");
        msg.setLoginStatus(false);
        msg.setAdmin(false);
        send(msg, client);
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
            NewDeviceMessage msg = new NewDeviceMessage(device.getDeviceID(), device.getName(), device.getType());
            send(msg, client);
        }
    }

    private void updateDeviceDetails(AbstractDeviceMessage msg, ConnectionToClient client) {
        //System.out.println("Device details received." + msg.getDeviceID());
        //get device from list
        SmartDevice device = devices.get(msg.getDeviceID()-1);
        //update device
        device.update(msg);
        //send device to client
        send(device.PrepareMessage(), client);
    }

    private void sendDetails(NewDeviceMessage msg, ConnectionToClient client) {
        //get deviceID from message and get device from list
        SmartDevice device = devices.get((msg).getDeviceID()-1);
        //System.out.println("Sending details for device " + device.getName() + " to client " + clientIDList.get(clientList.indexOf(client)));
        send(device.PrepareMessage(), client);
        }





    public void newDevice(SmartDevice device){
        devices.add(device);
        sendToAllClients(new NewDeviceMessage(device.getDeviceID(), device.getName(), device.getType()));
    }

    private void send(Object msg, ConnectionToClient client){
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            //System.out.println("Error sending message to client.");
            throw new RuntimeException(e);
        }
    }
}
