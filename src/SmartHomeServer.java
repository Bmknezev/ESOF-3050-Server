//-----------------------------------------------------------------
// SmartHomeServer.java
// Group 2
// Description: This class represents the Smart Home Server managing various smart devices and client connections.
// Created By: Braydon
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
import java.util.TimerTask;

public class SmartHomeServer extends AbstractServer {
    List<SmartDevice> devices = new java.util.ArrayList<>();
    List<User> users = new java.util.ArrayList<>();
    private int deviceID = 0;
    private int userID = 1;
    Timer timer = new Timer(); //this is a timer
    Timer updateTimer = new Timer(); //this is a timer
    
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public SmartHomeServer(int port) {
        super(port);

        //add usernames and passwords
        users.add(new User(userID, "admin", "admin", true));
        userID++;
        users.add(new User(userID, "user", "user", false));
        userID++;
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
                NewDeviceMessage message = ((NewDeviceMessage)msg);
                if(message.getDeviceID() == -1){
                    addNewDevice(message, client);
                }else
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
            case 6:
                //client is sending new pin details
                //System.out.println("Pin details received.");
                if(((PinMessage)msg).getNewPin() == -1)
                    CheckPIN((PinMessage) msg, client);
                else
                    ChangePIN((PinMessage) msg, client);

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

    private void CheckPIN(PinMessage msg, ConnectionToClient client) {
        //get device from list
        for(SmartDevice device : devices){
            if(device.getDeviceID() == msg.getDeviceID()){
                if(device.getType().equals("Smart Lock")){
                    if(msg.getPin() == ((SmartLock)device).getPIN()){
                        msg.setPinStatus(true);
                        send(msg, client);
                        System.out.println("PIN correct.");
                        return;
                    }
                }
                if(device.getType().equals("Smart Garage Door")){
                    if(msg.getPin() == ((SmartGarageDoor)device).getPIN()){
                        msg.setPinStatus(true);
                        send(msg, client);
                        System.out.println("PIN correct.");
                        return;
                    }
                }

                //if it gets here, the pin was incorrect
                msg.setPinStatus(false);
                send(msg, client);
                System.out.println("PIN incorrect.");
            }
        }
    }

    private void ChangePIN(PinMessage msg, ConnectionToClient client) {
        //get device from list
        for(SmartDevice device : devices){
            if(device.getDeviceID() == msg.getDeviceID()){
                if(device.getType().equals("Smart Lock")){
                    if(msg.getPin() == ((SmartLock)device).getPIN()){
                        ((SmartLock)device).setPIN(msg.getNewPin());
                        msg.setPinStatus(true);
                        send(msg, client);
                        System.out.println("PIN changed.");
                        return;
                    }
                }
                if(device.getType().equals("Smart Garage Door")){
                    if(msg.getPin() == ((SmartGarageDoor)device).getPIN()){
                        ((SmartGarageDoor)device).setPIN(msg.getNewPin());
                        msg.setPinStatus(true);
                        send(msg, client);
                        System.out.println("PIN changed.");
                        return;
                    }
                }

                //if it gets here, the pin was incorrect
                msg.setPinStatus(false);
                send(msg, client);
                System.out.println("PIN change failed.");
            }
        }
    }

    private void addNewDevice(NewDeviceMessage message, ConnectionToClient client) {
        //System.out.println("Adding new device.");
        SmartDevice device;
        switch (message.getDeviceTypeNumber()){
            case 0:
                device = new SmartLight(deviceID, message.getDeviceName(), this);
                break;
            case 1:
                device = new SmartLock(deviceID, message.getDeviceName(),message.getPIN(), this);
                break;
            case 2:
                device = new SmartThermostat(deviceID, message.getDeviceName(), this);
                break;
            case 3:
                device = new SmartCoffeeMachine(deviceID, message.getDeviceName(), this);
                break;
            case 4:
                device = new SmartGarageDoor(deviceID, message.getDeviceName(), message.getPIN(), this);
                break;
            default:
                System.out.println("Error: Device type not found");
                return;
        }
        deviceID++;
        newDevice(device);
    }

    private void modifyUser(UserListMessage msg, ConnectionToClient client) {
        //check if deleting user
        if(msg.getPassword().equals("delete")){
            //search through all users for user to delete
            for(User user : users){
                if(user.getUserID() == msg.getUserID()){
                    //check if deleting current user
                    if(user.getUsername().equals(msg.getUsername())){
                        System.out.println("Cannot delete current user.");
                        send(new UserListMessage(-2, msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                        return;
                    }
                    System.out.println("User deleted.");
                    users.remove(user);
                    return;
                }
            }
        }

        //this is now for updating users

        //check if username or password is empty
        if(msg.getUsername().isEmpty() || msg.getPassword().isEmpty()){
            System.out.println("Username or password is empty.");
            send(new UserListMessage(-4, msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
            return;
        }

        //check if username already used
        for(User user : users){
            if(user.getUserID() != msg.getUserID()) //check if username is from current user
                if (user.getUsername().equals(msg.getUsername())) {
                    System.out.println("User already exists.");
                    send(new UserListMessage(-1, msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                    return;
                }
        }

        //check for how many admins will be left after the update
        if(!msg.getAdmin()){
            int tmp = 0;
            for(User user : users) {
                if(user.getAdmin())
                    tmp++;

            }
            for(User user : users) {
                if(user.getUserID() == msg.getUserID())
                    if(user.getAdmin())
                        tmp--;
            }
            //if there will be no admins left, don't update
            if(tmp <= 0){
                send(new UserListMessage(-3,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                return;
            }

        }
        //update user
        for(User user : users){
            if(user.getUserID() == msg.getUserID()){
                user.update(msg);
                break;
            }
        }
    }

    private void AddUser(UserListMessage msg, ConnectionToClient client) {
        if(msg.getUsername().isEmpty() || msg.getPassword().isEmpty()){
            System.out.println("Username or password is empty.");
            send(new UserListMessage(-4,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
            return;
        }
        //check if user exists
        for(User user : users) {
            if (user.getUsername().equals(msg.getUsername())) {
                System.out.println("User already exists.");
                send(new UserListMessage(-1,msg.getUsername(), msg.getPassword(), msg.getAdmin(), false), client);
                return;
            }
        }
        users.add(new User(userID, msg.getUsername(), msg.getPassword(), msg.getAdmin()));
        userID++;
    }

    private void SendUsers(ConnectionToClient client) {
        sendToAllClients(new NewDeviceMessage(-5, "", ""));
        for(User user : users) {
            UserListMessage msg = user.prepareMessage();
            msg.setNewUser(true);
            sendToAllClients(msg);
        }
    }

    private void Login(LoginMessage msg, ConnectionToClient client) {
        //System.out.println("Login details received.");
        //check if username and password are correct
        for(User user : users) {
            if (user.getUsername().equals(msg.getUsername()) && user.getPassword().equals(msg.getPassword())) {
                //System.out.println("Login successful.");
                msg.setLoginStatus(true);
                msg.setAdmin(user.getAdmin());
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
        for(SmartDevice device : devices){
            if(device.getDeviceID() == msg.getDeviceID()){
                //schedule task if device is found
                timer.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        device.Automation(msg);
                    }
                }, msg.getDate());
                return;
            }
        }


    }

    private void SendDevices(ConnectionToClient client) {
        for (SmartDevice device : devices) {
            NewDeviceMessage msg = new NewDeviceMessage(device.getDeviceID(), device.getName(), device.getType());
            send(msg, client);
        }
    }

    private void updateDeviceDetails(AbstractDeviceMessage msg, ConnectionToClient client) {
        //get device from list
        for (SmartDevice device : devices) {
            if (device.getDeviceID() == msg.getDeviceID()) {
                //update device
                device.update(msg);
                //send device to client
                send(device.PrepareMessage(), client);
                return;
            }
        }
    }

    private void sendDetails(NewDeviceMessage msg, ConnectionToClient client) {
        if(msg.getDeviceType().equals("delete")){
            sendToAllClients(new NewDeviceMessage(-5, msg.getDeviceName(), msg.getDeviceType()));
            //delete device
            for(SmartDevice d : devices){
                if(d.getDeviceID() == msg.getDeviceID()){
                    devices.remove(d);
                    break;
                }
            }
            for(SmartDevice d : devices){
                sendToAllClients(new NewDeviceMessage(d.getDeviceID(), d.getName(), d.getType()));
            }
            return;
        }
        //get deviceID from message and get device from list
        for(SmartDevice device : devices){
            if(device.getDeviceID() == msg.getDeviceID()){
                //send device to client
                send(device.PrepareMessage(), client);
                return;
            }
        }
    }





    public void newDevice(SmartDevice device){
        sendToAllClients(new NewDeviceMessage(-5, device.getName(), device.getType()));
        devices.add(device);
        for(SmartDevice d : devices){
            sendToAllClients(new NewDeviceMessage(d.getDeviceID(), d.getName(), d.getType()));
        }

        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                device.timerUpdate();

            }
        }, 0, 1000);
    }

    private void send(Object msg, ConnectionToClient client){
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            //System.out.println("Error sending message to client.");
            throw new RuntimeException(e);
        }catch (Exception e){
            //System.out.println("Error sending message to client.");
            e.printStackTrace();
        }
    }
}
