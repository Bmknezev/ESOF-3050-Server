package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.LightMessage;

public class SmartLight extends SmartDevice{
    private String colour; //hexadecimal colour value (e.g. 0x000000 is black, 0xFFFFFF is white)
    private int brightness; //brightness value from 0 to 100
    private boolean lightStatus; //true if light is on, false if light is off

    public SmartLight(String name, int id, boolean connectionStatus, int battery, boolean status, String colour, int brightness, boolean lightStatus, AbstractServer server){
        super(id, name, connectionStatus, battery, status, server);
        this.colour = colour;
        this.brightness = brightness;
        this.lightStatus = lightStatus;
    }

    public void setColour(String colour){
        this.colour = colour;
    }

    public void setBrightness(int brightness){
        this.brightness = brightness;
    }

    public String getColour(){
        return colour;
    }

    public float getBrightness(){
        return brightness;
    }

    public void setLightStatus(boolean lightStatus){
        this.lightStatus = lightStatus;
    }

    public boolean getLightStatus(){
        return lightStatus;
    }

    @Override
    public void update(AbstractDeviceMessage msg) {
        LightMessage message = (LightMessage) msg;
        super.update(msg);
        setColour(message.getColour());
        setBrightness(message.getBrightness());
        setLightStatus(message.getLightStatus());

    }

    @Override
    public Object PrepareMessage() {
        return new LightMessage(getDeviceID(), getName(), getColour(), (int) getBrightness(), getLightStatus());
    }

    @Override
    public String getType() {
        return "Smart Light";
    }

    @Override
    public void timerUpdate(){
        //this method is called every second by the timer
        //it is used to update the device's status
        //this is where the device would check if it is still connected to the server
        //and update its battery level
        //this is run on the JavaFX thread
        //this method is empty because the light does not need to update anything
    }
}
