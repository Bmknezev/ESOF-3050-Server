//-----------------------------------------------------------------
// AbstractAutomationMessage.java
// Group 2
// Description: This abstract class serves as a base for messages related to automation, containing information about the device ID and date for automation messages.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - deviceID: an integer representing the ID of the device
//    - date: a Date object indicating the timestamp of the message
//
// Error Handling:
//
//
//-----------------------------------------------------------------

package messages.automations;

import messages.AbstractMessage;

import java.util.Date;

public class AbstractAutomationMessage extends AbstractMessage {

    private int deviceID, deviceType;
    private Date date;

    public AbstractAutomationMessage(int deviceID, Date date, int deviceType) {
        super(4);
        this.deviceID = deviceID;
        this.date = date;
        this.deviceType = deviceType;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public Date getDate() {
        return date;
    }

    public int getDeviceType() {
        return deviceType;
    }

}
