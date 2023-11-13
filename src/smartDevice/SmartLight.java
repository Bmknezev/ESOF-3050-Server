package smartDevice;

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

    public String getDetails(){
        return super.getDeviceID() + "|" + super.getName() + "|" + lightStatus + "|" + brightness + "|" + colour;
    }

    public String toString(){
        return super.getName() + "|" + "Smart Light" + "|" + super.getDeviceID();
    }

    @Override
    public void update(String[] s) {

        for(int i = 0; i < s.length; i+= 2){

            switch (s[i]) {
                case "lightStatus":
                    System.out.println("Updating Smart Light");
                    setLightStatus(Boolean.parseBoolean(s[i+1]));
                    break;
                case "brightness":
                    System.out.println("Updating brightness");
                    setBrightness(Integer.parseInt(s[i+1]));
                    break;
                case "colour":
                    setColour(Integer.parseInt(s[i+1]));
                    break;
                default:
                    break;
            }
        }





        //System.out.println("Updating Smart Light");
        //setLightStatus(Boolean.parseBoolean(s[0]));
        //setBrightness(Integer.parseInt(s[1]));
        //setColour(Integer.parseInt(s[2]));
    }


}
