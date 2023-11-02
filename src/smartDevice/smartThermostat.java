package smartDevice;
public class smartThermostat extends SmartDevice{
    private float temperature;
    private float setpoint;
    private boolean heatEnabled;
    private boolean coolEnabled;

    public smartThermostat(boolean connectionStatus, int battery, boolean status, float temperature, float setpoint, boolean heatEnabled, boolean coolEnabled){
        super(connectionStatus, battery, status);
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


}
