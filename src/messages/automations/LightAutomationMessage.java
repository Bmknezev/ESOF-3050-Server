//-----------------------------------------------------------------
// LightAutomationMessage.java
// Group 2
// Description: This class represents a message related to light automation, storing information about the device ID, color, brightness, and status of the light along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - colour: a String representing the color of the light
//    - brightness: an integer indicating the brightness level of the light
//    - lightStatus: a boolean indicating the status of the light (on/off)
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages.automations;

import java.util.Date;

public class LightAutomationMessage extends AbstractAutomationMessage{
    private String colour;
    private int brightness;
    private boolean lightStatus;

    public LightAutomationMessage(int deviceID, String colour, int brightness, boolean lightStatus, Date date) {
        super(deviceID, date, 0);
        this.colour = colour;
        this.brightness = brightness;
        this.lightStatus = lightStatus;
    }

    public String getColour() {
        return colour;
    }

    public int getBrightness() {
        return brightness;
    }

    public boolean getLightStatus() {
        return lightStatus;
    }
}
