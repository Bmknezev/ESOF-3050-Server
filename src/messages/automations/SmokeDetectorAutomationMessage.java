//-----------------------------------------------------------------
// SmokeDetectorAutomation.java
// Group 2
// Description: This class represents a message related to smoke detector automation, storing information about the device ID and the alarm status of the smoke detector along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - alarm: a boolean indicating the alarm status of the smoke detector
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages.automations;

import java.util.Date;

public class SmokeDetectorAutomationMessage extends AbstractAutomationMessage{
    private boolean alarm;

    public SmokeDetectorAutomationMessage(int deviceID, boolean alarm, Date date) {
        super(deviceID, date, 5);
        this.alarm = alarm;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
