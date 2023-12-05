//-----------------------------------------------------------------
// SmartSmokeDetector.java
// Group 2
// Description: Represents a smart smoke detector device. Manages variables related to test status, alarm status, and the last test date. Includes methods for updating, preparing messages, and timer updates.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - lastTested: date of the last smoke detector test
//    - testStatus: indicates whether the smoke detector needs testing or not
//    - alarmStatus: indicates whether the smoke detector alarm is ready or not
//    - alarm: indicates if the smoke detector alarm is currently going off
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.SmokeDetectorMessage;

import java.util.Date;

public class SmartSmokeDetector extends SmartDevice{
    private Date lastTested; //date of last test
    private boolean testStatus; //true if need to test, false if dont need to test
    private boolean alarmStatus; //true if alarm is ready, false if alarm is not ready
    private boolean alarm; //true if alarm is going off, false if alarm is not going off

    /**
     * This is the constructor for the SmartSmokeDetector class.
     * @param name device name
     * @param id device id
     * @param server server
     */
    public SmartSmokeDetector(String name, int id, AbstractServer server){
        super(id, name, server);
        this.lastTested = new Date();
        this.testStatus = true;
        this.alarmStatus = false;
        this.alarm = false;
    }

    public SmartSmokeDetector(int id, String deviceName, AbstractServer smartHomeServer) {
        super(id, deviceName, smartHomeServer);
    }

    public void setLastTested(Date lastTested){
        this.lastTested = lastTested;
    }

    public void setTestStatus(boolean testStatus){
        this.testStatus = testStatus;
    }

    public void setAlarmStatus(boolean alarmStatus){
        this.alarmStatus = alarmStatus;
    }

    public void setAlarm(boolean alarm){
        this.alarm = alarm;
    }

    public Date getLastTested(){
        return lastTested;
    }

    public boolean getTestStatus(){
        return testStatus;
    }

    public boolean getAlarmStatus(){
        return alarmStatus;
    }

    public boolean getAlarm(){
        return alarm;
    }


    public void update(AbstractDeviceMessage msg){
        SmokeDetectorMessage message = (SmokeDetectorMessage) msg;
        super.update(msg);
        setLastTested(message.getLastTested());
        setTestStatus(message.getTestStatus());
        setAlarmStatus(message.getAlarmStatus());
    }
    @Override
    public Object PrepareMessage() {
        return new SmokeDetectorMessage(getDeviceID(), getName(), getLastTested(), getTestStatus(), getAlarmStatus());
    }

    @Override
    public String getType() {
        return "Smart Smoke Detector";
    }

    @Override
    public void timerUpdate(){
        //this method is called every second by the server
        //it is used to update the smoke detector's alarm status
        //if the alarm is going off, it will turn off after 10 seconds
        if (alarm){
            alarm = false;
        }
        server.sendToAllClients(PrepareMessage());
    }


}
