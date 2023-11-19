package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.ThermostatMessage;

public class SmartThermostat extends SmartDevice{
    private float temperature; //the current temperature
    private float setpoint; //the temperature the thermostat is set to
    private boolean heatEnabled; //if true, heat is enabled
    private boolean coolEnabled; //if true, cool is enabled
    private Boolean mode; //the mode the thermostat is in (heat, cool, off)

    /**
     * This is the constructor for the SmartThermostat class.
     * @param name device name
     * @param id device id
     * @param connectionStatus connection status
     * @param battery battery level
     * @param status device status, online or offline
     * @param temperature current temperature
     * @param setpoint temperature the thermostat is set to
     * @param heatEnabled if true, heat is enabled
     * @param coolEnabled if true, cool is enabled
     * @param server server
     */
    public SmartThermostat(String name, int id, boolean connectionStatus, int battery, boolean status, float temperature, float setpoint, boolean heatEnabled, boolean coolEnabled, AbstractServer server){
        super(id, name, connectionStatus, battery, status, server);
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
        //only update value if it is not 0
        if(message.getTemperature() != 0)
            this.temperature = message.getTemperature();
        if(message.getSetpoint() != 0)
            this.setpoint = message.getSetpoint();
        this.heatEnabled = message.getHeatEnabled();
        this.coolEnabled = message.getCoolEnabled();
    }

    @Override
    public Object PrepareMessage() {
        //determine mode based on heat and cool enabled, as well as setpoint and temperature values
        //mode: 0 = off, 1 = heat, 2 = cool
        int mode;
        if(temperature < setpoint && heatEnabled)
            mode = 1;
        else if(temperature > setpoint && coolEnabled)
            mode = 2;
        else
            mode = 0;
        return new ThermostatMessage(getDeviceID(), getName(), temperature, setpoint, heatEnabled, coolEnabled, mode);
    }

    @Override
    public String getType() {
        return "Smart Thermostat";
    }

    @Override
    public void timerUpdate() {
        server.sendToAllClients(PrepareMessage());
    }

}
