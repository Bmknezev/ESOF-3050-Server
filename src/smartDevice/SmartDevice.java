package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.automations.AbstractAutomationMessage;

import java.io.Serializable;

public abstract class SmartDevice implements Serializable {
    /**
     * This is the abstract class for all smart devices. It contains all the methods and variables that are common to all devices.
     * It also contains the abstract methods that are implemented in the subclasses.
     */
    private boolean connectionStatus; //device connected to network
    private int battery; //battery level

    private boolean status; //device ready to use
    private String name;
    private int deviceID;
    protected AbstractServer server;


    /**
     * This is the constructor for the SmartDevice class.
     * @param id device id
     * @param name device name
     * @param connectionStatus connection status
     * @param battery battery level
     * @param status device status, online or offline
     * @param server server
     */
    public SmartDevice(int id, String name, boolean connectionStatus, int battery, boolean status, AbstractServer server){

        this.name = name;
        this.deviceID = id;
        this.connectionStatus = connectionStatus;
        this.battery = battery;
        this.status = status;
        this.server = server;
    }

    /**
     * This method sets the connection status of the device.
     * @param connectionStatus connection status
     */
    public void setConnectionStatus(boolean connectionStatus){
        this.connectionStatus = connectionStatus;
    }

    /**
     * This method sets the battery level of the device.
     * @param battery battery level
     */
    public void setBattery(int battery){
        this.battery = battery;
    }

    /**
     * This method sets the status of the device.
     * @param status status
     */
    public void setStatus(boolean status){
        this.status = status;
    }

    /**
     * This method returns the connection status of the device.
     * @return connectionStatus connection status
     */
    public boolean getConnectionStatus(){
        return connectionStatus;
    }

    /**
     * This method returns the battery level of the device.
     * @return battery
     */
    public int getBattery(){
        return battery;
    }

    /**
     * This method returns the status of the device.
     * @return status
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * This method returns the name of the device.
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * This method sets the name of the device.
     * @param name device name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * This method returns the device id of the device.
     * @return deviceID
     */
    public int getDeviceID(){
        return deviceID;
    }

    /**
     * This method sets the device id of the device.
     * @param id device id
     */
    public void setDeviceID(int id){
        this.deviceID = id;
    }

    /**
     * This method updates the device with the values from the server.
     * @param msg message from server
     */
    public void update(AbstractDeviceMessage msg){
        this.name = msg.getName();
    }


    /**
     * These methods are abstract and are implemented in the subclasses.
     */
    public abstract Object PrepareMessage();

    public abstract String getType();

    public abstract void timerUpdate();

    public void Automation(AbstractAutomationMessage msg) {
    }
}
