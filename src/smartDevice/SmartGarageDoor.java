//-----------------------------------------------------------------
// SmartGarageDoor.java
// Group 2
// Description: This class represents a smart garage door device. It contains variables and methods for managing the garage door's status, safety features, password authentication, and communication with the server.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - safteySwitch: a boolean indicating if an object is detected under the door
//    - doorStatus: a boolean indicating if the door is open or closed
//    - moving: a boolean indicating if the door is in motion
//    - usePassword: a boolean indicating if a password is required to open the door
//    - pin: an integer storing the password to open the door
//    - passwordAttempts: an integer tracking the number of incorrect password attempts
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.GarageDoorMessage;

public class SmartGarageDoor extends SmartDevice{
    private boolean safteySwitch; //true if object is detected under door, false if no object is detected
    private boolean doorStatus; //true if door is open, false if door is closed
    private boolean moving; //true if door is moving, false if door is not moving
    private boolean usePassword; //true if password is needed to open door, false if password is not needed
    private int pin; //password to open door
    private int passwordAttempts; //number of times password has been entered incorrectly

    /** This is the constructor for the SmartGarageDoor class.
     * @param name device name
     * @param id device id
     * @param safteySwitch true if object is detected under door, false if no object is detected
     * @param doorStatus true if door is open, false if door is closed
     * @param moving true if door is moving, false if door is not moving
     * @param server server
     */
    public SmartGarageDoor(String name, int id, boolean safteySwitch, boolean doorStatus, boolean moving, AbstractServer server){
        super(id, name, server);
        this.safteySwitch = safteySwitch;
        this.doorStatus = doorStatus;
        this.moving = moving;
        this.passwordAttempts = 0;
    }

    public SmartGarageDoor(int id, String deviceName, int PIN, AbstractServer smartHomeServer) {
        super(id, deviceName, smartHomeServer);
        this.safteySwitch = false;
        this.doorStatus = false;
        this.moving = false;
        this.passwordAttempts = 0;
        this.pin = PIN;

    }

    public void setSafteySwitch(boolean safteySwitch){
        this.safteySwitch = safteySwitch;
    }

    public void setDoorStatus(boolean doorStatus){
        this.doorStatus = doorStatus;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    public void setUsePassword(boolean usePassword, int pin){
        this.usePassword = usePassword;
        this.setPIN(pin);
    }

    public void setPIN(int pin){
        this.pin = pin;
    }

    public boolean getSafteySwitch(){
        return safteySwitch;
    }

    public boolean getDoorStatus(){
        return doorStatus;
    }

    public boolean getMoving(){
        return moving;
    }

    public boolean getUsePassword(){
        return usePassword;
    }

    public int getPIN(){
        return pin;
    }

    public Boolean authenticatePassword(int pin){
        if (this.pin == pin){
            passwordAttempts = 0;
            return true;
        }
        else{
            passwordAttempts++;
            if(passwordAttempts >= 3){
                this.alertOwner();
            }
            return false;
        }
    }

    public void alertOwner(){
        System.out.println("ALERT: Someone is trying to break into your house!");
    }

    @Override
    public void update(AbstractDeviceMessage msg) {
        super.update(msg);
        GarageDoorMessage message = (GarageDoorMessage) msg;
        System.out.println(message.getDoorStatus());
        setSafteySwitch(message.getSafteySwitch());
        setDoorStatus(message.getDoorStatus());
        setMoving(message.getMoving());
        setUsePassword(message.getUsePassword(), message.getPassword());

    }

    @Override
    public Object PrepareMessage() {
        return new GarageDoorMessage(getDeviceID(), getName(), getSafteySwitch(), getDoorStatus(), getMoving(), getUsePassword(), getPIN(), passwordAttempts);
    }

    @Override
    public String getType() {
        return "Smart Garage Door";
    }

    @Override
    public void timerUpdate() {
        server.sendToAllClients(PrepareMessage());
    }
}
