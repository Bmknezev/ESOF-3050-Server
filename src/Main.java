import smartDevice.SmartLight;
import smartDevice.SmartLock;
import smartDevice.SmartThermostat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //making a few devices for testing
        SmartLight l = new SmartLight("light 1", 1,true, -1,true,0x000000,100,true );
        SmartLight l2 = new SmartLight("light 2", 2,true, -1,true,0x000000,100,true );

        SmartLock lock = new SmartLock("lock 1", 3,true, -1,true, true);

        SmartThermostat thermostat = new SmartThermostat("Thermostat 1" , 4,true, -1,true, 20, 20, true, false);

        //starting server and adding the devices
        SmartHomeServer s = new SmartHomeServer(19920);
        s.newDevice(l);
        s.newDevice(l2);
        s.newDevice(lock);
        s.newDevice(thermostat);

        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}