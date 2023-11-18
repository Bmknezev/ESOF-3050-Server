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

    public SmartSmokeDetector(String name, int id, boolean connectionStatus, int battery, boolean status, AbstractServer server){
        super(id, name, connectionStatus, battery, status, server);
        this.lastTested = new Date();
        this.testStatus = true;
        this.alarmStatus = false;
        this.alarm = false;
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
    }


}
