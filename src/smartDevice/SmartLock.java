package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.LockMessage;

public class SmartLock extends SmartDevice{
    private boolean lockStatus; //true = locked, false = unlocked
    private int password; //password to unlock the door
    private int timer; //timer to lock the door after a certain amount of time
    private String pin; //pin to unlock the door

    /**
     * This is the constructor for the SmartLock class.
     * @param name device name
     * @param id device id
     * @param connectionStatus connection status
     * @param battery battery level
     * @param status device status, online or offline
     * @param lockStatus lock status, locked or unlocked
     * @param server server
     */
    public SmartLock(String name, int id, boolean connectionStatus, int battery, boolean status, boolean lockStatus, String pin, AbstractServer server){
        super(id, name, connectionStatus, battery, status, server);
        this.lockStatus = lockStatus;
        this.pin = pin;
    }

    public void setLockStatus(boolean lockStatus){
        this.lockStatus = lockStatus;
    }

    public void setPassword(int password){
        this.password = password;
    }

    public boolean getLockStatus(){
        return lockStatus;
    }

    public int getPassword(){
        return password;
    }

    public Boolean authenticatePassword(int password){
        if (this.password == password){
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
        this.password = message.getPassword();
        this.timer = message.getTimer();

    }

    @Override
    public Object PrepareMessage() {
        return new LockMessage(getDeviceID(), getName(), getLockStatus(), getPassword(), getTimer(), getPIN());
    }

    private String getPIN() {
        return pin;
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
