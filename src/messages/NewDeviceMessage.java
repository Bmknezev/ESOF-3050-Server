//-----------------------------------------------------------------
// NewDeviceMessage.java
// Group 2
// Description: This class represents a message related to a new device, storing information about the device name, ID, type, and PIN (if applicable).
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - deviceName: a String representing the name of the new device
//    - deviceID: an integer representing the ID of the new device
//    - deviceTypeNumber: an integer representing the type of the new device
//    - deviceType: a String representing the type of the new device (e.g., "Smart Light", "Smart Lock", etc.)
//    - pin: an integer representing the PIN for the new device (if applicable)
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

import ClientServer.SmartDeviceIndex;
import messages.client.Listable;

public class NewDeviceMessage extends AbstractMessage implements Listable {
    //request a new device
    private String deviceName;
    private int deviceID, deviceTypeNumber;
    private String deviceType;
    private int pin;

    public NewDeviceMessage(int id, String name, String deviceType) {
        //constructor used by server
        super(2);
        this.deviceName = name;
        this.deviceID = id;
        this.deviceType = deviceType;

        determineDeviceTypeNumber(deviceType);
    }

    private void determineDeviceTypeNumber(String deviceType) {
        deviceTypeNumber = SmartDeviceIndex.getDeviceTypeNumber(deviceType);
    }

    public NewDeviceMessage(int id) {
        //constructor used by client
        super(2);
        this.deviceID = id;
        this.deviceName = "";
        this.deviceType = "";
    }


    public String getDeviceName() {
        return deviceName;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public int getDeviceTypeNumber() {
        return deviceTypeNumber;
    }


    public String getDeviceType() {
        return deviceType;
    }

    @Override
    public String getNameListable() {
        return deviceName;
    }

    @Override
    public String getCategoryListable() {
        return deviceType;
    }

    @Override
    public int getIDListable() {
        return deviceID;
    }

    public int getPIN() {
        return pin;
    }

    public void setPIN(int pin) {
        this.pin = pin;
    }
}
