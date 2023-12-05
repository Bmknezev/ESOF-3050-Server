//-----------------------------------------------------------------
// ThermostatAutomationMessage.java
// Group 2
// Description: This class represents a message related to thermostat automation, storing information about the device ID, temperature setting, and status of the thermostat along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - temperature: an integer representing the temperature set on the thermostat
//    - thermostatStatus: a boolean indicating the status of the thermostat (on/off)
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages.automations;

import java.util.Date;

public class ThermostatAutomationMessage extends AbstractAutomationMessage{
    private int temperature;
    private boolean thermostatStatus;

    public ThermostatAutomationMessage(int deviceID, int temperature, boolean thermostatStatus, Date date) {
        super(deviceID, date, 2);
        this.temperature = temperature;
        this.thermostatStatus = thermostatStatus;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean getThermostatStatus() {
        return thermostatStatus;
    }
}
