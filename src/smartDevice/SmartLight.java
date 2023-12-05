//-----------------------------------------------------------------
// SmartLight.java
// Group 2
// Description: Represents a smart light device. It manages variables related to light color, brightness, and status. Additionally, it includes methods for updating, preparing messages, and handling automation.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - colour: stores the hexadecimal colour value (e.g., 0x000000 for black, 0xFFFFFF for white)
//    - brightness: indicates the brightness level ranging from 0 to 100
//    - lightStatus: represents whether the light is on (true) or off (false)
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
import messages.automations.LightAutomationMessage;
import messages.server.LightMessage;


public class SmartLight extends SmartDevice{
    private String colour; //hexadecimal colour value (e.g. 0x000000 is black, 0xFFFFFF is white)
    private int brightness; //brightness value from 0 to 100
    private boolean lightStatus; //true if light is on, false if light is off


    /**
     * This is the constructor for the SmartLight class.
     * @param name device name
     * @param id device id
     * @param colour light colour
     * @param brightness light brightness
     * @param lightStatus light status
     * @param server server
     */
    public SmartLight(String name, int id, String colour, int brightness, boolean lightStatus, AbstractServer server){
        super(id, name, server);
        this.colour = colour;
        this.brightness = brightness;
        this.lightStatus = lightStatus;
    }

    public SmartLight(int deviceID, String deviceName, AbstractServer server) {
        super(deviceID, deviceName, server);
        this.colour = "ffff00";
        this.brightness = 100;
        this.lightStatus = false;
    }

    /**
     * This method sets the colour of the light.
     * @param colour light colour
     */
    public void setColour(String colour){
        this.colour = colour;
    }

    /**
     * This method sets the brightness of the light.
     * @param brightness light brightness
     */
    public void setBrightness(int brightness){
        this.brightness = brightness;
    }

/**
     * This method gets the colour of the light.
     * @return colour light colour
     */
    public String getColour(){
        return colour;
    }

    /**
     * This method gets the brightness of the light.
     * @return brightness light brightness
     */
    public float getBrightness(){
        return brightness;
    }

    /**
     * This method sets the status of the light.
     * @param lightStatus light status
     */
    public void setLightStatus(boolean lightStatus){
        this.lightStatus = lightStatus;
    }

    /**
     * This method gets the status of the light.
     * @return lightStatus light status
     */
    public boolean getLightStatus(){
        return lightStatus;
    }

    /**
     * This method updates the device's status.
     * @param msg message from server
     */
    @Override
    public void update(AbstractDeviceMessage msg) {
        LightMessage message = (LightMessage) msg;
        super.update(msg);
        setColour(message.getColour());
        setBrightness(message.getBrightness());
        setLightStatus(message.getLightStatus());

        System.out.println(lightStatus + " " + message.getLightStatus());


    }

    /**
     * This method prepares a message to be sent to the server.
     * @return message
     */
    @Override
    public Object PrepareMessage() {
        System.out.println("Preparing message: " + getDeviceID() + " " + getName() + " " + getColour() + " " + getBrightness() + " " + getLightStatus());
        return new LightMessage(getDeviceID(), getName(), getColour(), (int) getBrightness(), getLightStatus());
    }

    /**
     * This method returns the type of the device.
     * @return type
     */
    @Override
    public String getType() {
        return "Smart Light";
    }

    /**
     * This method updates the client every 3 seconds.
     */
    @Override
    public void timerUpdate() {
            server.sendToAllClients(PrepareMessage());

    }

    @Override
    public void Automation(AbstractAutomationMessage msg) {
        LightAutomationMessage message = (LightAutomationMessage) msg;
        setLightStatus(message.getLightStatus());
        setColour(message.getColour());
        setBrightness(message.getBrightness());
        server.sendToAllClients(PrepareMessage());
    }

}
