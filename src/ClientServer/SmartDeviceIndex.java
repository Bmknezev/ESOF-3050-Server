//-----------------------------------------------------------------
// SmartDeviceIndex.java
// Group 2
// Description: Manages the indexing of Smart Devices based on their types.
// Created By: Braydon
// Edited By: Braydon
// Approved By: Braydon, Francisco, Liam
//-----------------------------------------------------------------

package ClientServer;

public class SmartDeviceIndex {

    public static String getDeviceType(int deviceTypeNumber) {
        switch (deviceTypeNumber) {
            case 0:
                return "Smart Light";
            case 1:
                return "Smart Lock";
            case 2:
                return "Smart Thermostat";
            case 3:
                return "Smart Coffee Machine";
            case 4:
                return "Smart Garage Door";
            default:
                return "Error: Device type not found";
        }
    }

    public static int getDeviceTypeNumber(String deviceType) {
        switch (deviceType) {
            case "Smart Light":
                return 0;
            case "Smart Lock":
                return 1;
            case "Smart Thermostat":
                return 2;
            case "Smart Coffee Machine":
                return 3;
            case "Smart Garage Door":
                return 4;
            default:
                return -1;
        }
    }
}
