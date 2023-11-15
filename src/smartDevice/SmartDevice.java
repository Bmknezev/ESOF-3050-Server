package smartDevice;

import messages.AbstractDeviceMessage;

import java.io.Serializable;

public abstract class SmartDevice implements Serializable {
    private boolean connectionStatus; //device connected to network
    private int battery; //battery level

    private boolean status; //device ready to use
    private String name;
    private int deviceID;


    public SmartDevice(int id, String name, boolean connectionStatus, int battery, boolean status){
        this.name = name;
        this.deviceID = id;
        this.connectionStatus = connectionStatus;
        this.battery = battery;
        this.status = status;
    }

    public void setConnectionStatus(boolean connectionStatus){
        this.connectionStatus = connectionStatus;
    }

    public void setBattery(int battery){
        this.battery = battery;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean getConnectionStatus(){
        return connectionStatus;
    }

    public int getBattery(){
        return battery;
    }

    public boolean getStatus(){
        return status;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getDeviceID(){
        return deviceID;
    }

    public void setDeviceID(int id){
        this.deviceID = id;
    }

    public void update(AbstractDeviceMessage msg){
        this.name = msg.getName();
    }


    public abstract Object PrepareMessage();

    public abstract String getType();
}
