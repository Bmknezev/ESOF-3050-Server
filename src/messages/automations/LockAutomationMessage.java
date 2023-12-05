//-----------------------------------------------------------------
// LockAutomationMessage.java
// Group 2
// Description: This class represents a message related to lock automation, storing information about the device ID, status of the lock, timer duration, personal identification number (PIN) along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - lockStatus: a boolean indicating the status of the lock (locked/unlocked)
//    - timer: an integer representing the timer duration for the lock
//    - pin: an integer representing the personal identification number for the lock
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages.automations;

import java.util.Date;

public class LockAutomationMessage extends AbstractAutomationMessage{
    private boolean lockStatus;
    private int timer;
    private int pin;

    public LockAutomationMessage(int deviceID, boolean lockStatus, int timer, int pin, Date date) {
        super(deviceID, date, 1);
        this.lockStatus = lockStatus;
        this.timer = timer;
        this.pin = pin;
    }

    public boolean getLockStatus() {
        return lockStatus;
    }

    public int getTimer() {
        return timer;
    }

    public int getPIN() {
        return pin;
    }
}
