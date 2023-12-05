//-----------------------------------------------------------------
// SmartDevice.java
// Group 2
// Description: This abstract class serves as the base for all smart devices. It contains common methods and variables shared among all smart devices. The abstract methods are implemented in subclasses.
// Created By: Braydon
// Edited By: Braydon, Liam
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - name: a String representing the name of the smart device
//    - deviceID: an integer representing the identification number of the device
//    - server: an instance of AbstractServer used for communication
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

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
    private String name;
    private int deviceID;
    protected AbstractServer server;


    /**
     * This is the constructor for the SmartDevice class.
     * @param id device id
     * @param name device name
     * @param server server
     */
    public SmartDevice(int id, String name, AbstractServer server){

        this.name = name;
        this.deviceID = id;
        this.server = server;
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
