//-----------------------------------------------------------------
// GarageDoorAutomationMessage.java
// Group 2
// Description: This class represents a message related to garage door automation, storing information about the device ID and the status of the garage door along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - doorStatus: a boolean indicating the status of the garage door (open/close)
//
// Error Handling:
//
//-----------------------------------------------------------------

package messages.automations;

import java.util.Date;

public class GarageDoorAutomationMessage extends AbstractAutomationMessage{
    private boolean doorStatus;

    public GarageDoorAutomationMessage(int deviceID, boolean doorStatus, Date date) {
        super(deviceID, date, 4);
        this.doorStatus = doorStatus;
    }

    public boolean getDoorStatus() {
        return doorStatus;
    }
}
