//-----------------------------------------------------------------
// SmartLock.java
// Group 2
// Description: Represents a smart lock device. Manages variables related to lock status, timer, and PIN for unlocking. Includes methods for authentication, updating, preparing messages, and handling alerts.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - lockStatus: indicates whether the lock is locked (true) or unlocked (false)
//    - timer: represents the time duration before the door automatically locks
//    - pin: stores the PIN required to unlock the door
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.LockMessage;

public class SmartLock extends SmartDevice{
    private boolean lockStatus; //true = locked, false = unlocked
    private int timer; //timer to lock the door after a certain amount of time
    private int pin; //pin to unlock the door

    /**
     * This is the constructor for the SmartLock class.
     * @param name device name
     * @param id device id
     * @param lockStatus lock status, locked or unlocked
     * @param server server
     */
    public SmartLock(String name, int id, boolean lockStatus, int pin, AbstractServer server){
        super(id, name, server);
        this.lockStatus = lockStatus;
        this.pin = pin;
    }

    public SmartLock(int id, String deviceName, int pin,AbstractServer smartHomeServer) {
        super(id, deviceName, smartHomeServer);
        this.lockStatus = false;
        this.pin = pin;

    }

    public void setLockStatus(boolean lockStatus){
        this.lockStatus = lockStatus;
    }


    public boolean getLockStatus(){
        return lockStatus;
    }


    public Boolean authenticatePassword(int pin){
        if (this.pin == pin){
            return true;
        }
        else{
            this.alertOwner();
            return false;
        }
    }

    public void setTimer(int timer){
        this.timer = timer;
    }

    public int getTimer(){
        return timer;
    }

    public void alertOwner(){
        System.out.println("ALERT: Someone is trying to break into your house!");
    }


    @Override
    public void update(AbstractDeviceMessage msg) {
        LockMessage message = (LockMessage) msg;
        super.update(msg);
        this.lockStatus = message.getLockStatus();
        this.pin = message.getPIN();
        this.timer = message.getTimer();

    }

    @Override
    public Object PrepareMessage() {
        return new LockMessage(getDeviceID(), getName(), getLockStatus(), getTimer(), getPIN());
    }

    public int getPIN() {
        return pin;
    }
    public void setPIN(int pin) {
        this.pin = pin;
    }

    @Override
    public String getType() {
        return "Smart Lock";
    }

    @Override
    public void timerUpdate() {
        //this method is called every 5 second by the timer
        server.sendToAllClients(PrepareMessage());
    }
}
