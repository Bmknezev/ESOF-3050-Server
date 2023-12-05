//-----------------------------------------------------------------
// AbstractDeviceMesasge.java
// Group 2
// Description: This abstract class serves as a base for messages related to devices, storing information about the device name and ID.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - deviceName: a String representing the name of the device
//    - deviceID: an integer representing the ID of the device
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public abstract class AbstractDeviceMessage extends AbstractMessage{
    private String deviceName;
    private int deviceID;

    public AbstractDeviceMessage(String name, int id){
        super(1);
        this.deviceName = name;
        this.deviceID = id;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public String getName() {
        return deviceName;
    }
}
