package smartDevice;
public class SmartThermostat extends SmartDevice{
    private float temperature; //the current temperature
    private float setpoint; //the temperature the thermostat is set to
    private boolean heatEnabled; //if true, heat is enabled
    private boolean coolEnabled; //if true, cool is enabled

    public SmartThermostat(int id, String name, boolean connectionStatus, int battery, boolean status, float temperature, float setpoint, boolean heatEnabled, boolean coolEnabled){
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
        System.out.println("Updating Smart Thermostat");
        setTemperature(Float.parseFloat(s[0]));
        setSetpoint(Float.parseFloat(s[1]));
        setHeatEnabled(Boolean.parseBoolean(s[2]));
        setCoolEnabled(Boolean.parseBoolean(s[3]));
    }

    @Override
    public String getDetails() {
        return null;
    }
}
