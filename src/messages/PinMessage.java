//-----------------------------------------------------------------
// PinMessage.java
// Group 2
// Description: This class represents a message related to a PIN change, storing information about the device ID, current PIN, new PIN, and the status of the PIN change.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - pin: an integer representing the current PIN
//    - newPin: an integer representing the new PIN
//    - deviceID: an integer representing the ID of the device
//    - pinStatus: a boolean indicating the status of the PIN change (true/false)
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public class PinMessage extends AbstractMessage{
    private int pin;
    private int newPin;
    private int deviceID;
    private boolean pinStatus;

    public PinMessage(int id, int pin, int newPin, boolean pinStatus) {
        super(6);
        this.pin = pin;
        this.deviceID = id;
        this.newPin = newPin;
        this.pinStatus = pinStatus;
    }

    public int getPin() {
        return pin;
    }

    public void setPinStatus(boolean pinStatus) {
        this.pinStatus = pinStatus;
    }

    public boolean getPinStatus() {
        return pinStatus;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public int getNewPin() {
        return newPin;
    }
}
