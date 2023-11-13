package smartDevice;
public class SmartThermostat extends SmartDevice{
    private float temperature; //the current temperature
    private float setpoint; //the temperature the thermostat is set to
    private boolean heatEnabled; //if true, heat is enabled
    private boolean coolEnabled; //if true, cool is enabled

    public SmartThermostat(String name, int id, boolean connectionStatus, int battery, boolean status, float temperature, float setpoint, boolean heatEnabled, boolean coolEnabled){
        super(id, name, connectionStatus, battery, status);
        this.temperature = temperature;
        this.setpoint = setpoint;
        this.heatEnabled = heatEnabled;
        this.coolEnabled = coolEnabled;
    }

    public void setTemperature(float temperature){
        this.temperature = temperature;
    }

    public void setSetpoint(float setpoint){
        this.setpoint = setpoint;
    }

    public void setHeatEnabled(boolean heatEnabled){
        this.heatEnabled = heatEnabled;
    }

    public void setCoolEnabled(boolean coolEnabled){
        this.coolEnabled = coolEnabled;
    }

    public float getTemperature(){
        return temperature;
    }

    public float getSetpoint(){
        return setpoint;
    }

    public boolean getHeatEnabled(){
        return heatEnabled;
    }

    public boolean getCoolEnabled(){
        return coolEnabled;
    }


    @Override
    public void update(String[] s) {
        for(int i = 0; i < s.length; i+= 2){
            switch (s[i]) {
                case "temperature":
                    System.out.println("Updating Smart Thermostat");
                    setTemperature(Float.parseFloat(s[i+1]));
                    break;
                case "setpoint":
                    System.out.println("Updating setpoint");
                    setSetpoint(Float.parseFloat(s[i+1]));
                    break;
                case "heatEnabled":
                    System.out.println("Updating heatEnabled");
                    setHeatEnabled(Boolean.parseBoolean(s[i+1]));
                    break;
                case "coolEnabled":
                    System.out.println("Updating coolEnabled");
                    setCoolEnabled(Boolean.parseBoolean(s[i+1]));
                    break;
            }
        }
    }

    @Override
    public String getDetails() {
        String mode;
        if(heatEnabled && temperature < setpoint){
            mode = "heating";
        }
        else if(coolEnabled && temperature > setpoint){
            mode = "cooling";
        }
        else{
            mode = "off";
        }
        return super.getDeviceID() + "|" + super.getName() + "|" + getTemperature() + "|" + getSetpoint() + "|" + mode;
    }

    public String toString(){
        return super.getName() + "|" + "Smart Thermostat" + "|" + super.getDeviceID();
    }
}
