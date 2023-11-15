package smartDevice;

import messages.AbstractDeviceMessage;
import messages.server.LightMessage;

public class SmartLight extends SmartDevice{
    private int colour; //hexadecimal colour value (e.g. 0x000000 is black, 0xFFFFFF is white)
    private int brightness; //brightness value from 0 to 100
    private boolean lightStatus; //true if light is on, false if light is off

    public SmartLight(String name, int id, boolean connectionStatus, int battery, boolean status, int colour, int brightness, boolean lightStatus){
        super(id, name, connectionStatus, battery, status);
        this.colour = colour;
        this.brightness = brightness;
        this.lightStatus = lightStatus;
    }

    public void setColour(int colour){
        this.colour = Integer.parseInt(String.valueOf(colour), 16);
    }

    public void setBrightness(int brightness){
        this.brightness = brightness;
    }

    public int getColour(){
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
}
