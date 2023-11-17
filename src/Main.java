import smartDevice.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //making a few devices for testing
        SmartLight l = new SmartLight("light 1", 1,true, -1,true,"0xffff80",100,true );
        SmartLight l2 = new SmartLight("light 2", 2,true, -1,true,"0x43820180",100,true );

        SmartLock lock = new SmartLock("lock 1", 3,true, -1,true, true);

        SmartThermostat thermostat = new SmartThermostat("Thermostat 1" , 4,true, 20,true, 20, 20, true, false);

        SmartCoffeeMachine coffeeMachine = new SmartCoffeeMachine("Coffee Machine 1", 5, true, -1, true);

        SmartGarageDoor garageDoor = new SmartGarageDoor("Garage Door 1", 6, true, -1, true, true, true, true);

        SmartSmokeDetector smokeDetector = new SmartSmokeDetector("Smoke Detector 1", 7, true, -1, true);

        //starting server and adding the devices
        SmartHomeServer s = new SmartHomeServer(19920);
        s.newDevice(l);
        s.newDevice(l2);
        s.newDevice(lock);
        s.newDevice(thermostat);
        s.newDevice(coffeeMachine);
        s.newDevice(garageDoor);
        s.newDevice(smokeDetector);


        coffeeMachine.setCoffeeBeanLevel(0.5);
        coffeeMachine.setWaterLevel(0.7);
        coffeeMachine.setServer(s);


        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}