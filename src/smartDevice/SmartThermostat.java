package smartDevice;

import messages.AbstractDeviceMessage;
import messages.ThermostatMessage;

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



    public void update(AbstractDeviceMessage msg) {
        ThermostatMessage message = (ThermostatMessage) msg;
        super.update(msg);
        this.temperature = message.getTemperature();
        this.setpoint = message.getSetpoint();
        this.heatEnabled = message.getHeatEnabled();
        this.coolEnabled = message.getCoolEnabled();
    }

    @Override
    public Object PrepareMessage() {
        return new ThermostatMessage(true, getDeviceID(), getName(), getConnectionStatus(), getBattery(), getStatus(), temperature, setpoint, heatEnabled, coolEnabled);
    }

}
