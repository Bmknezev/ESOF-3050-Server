import smartDevice.*;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    /**
     * This is the main method for the server. It creates a new server and a few devices for testing.
     * It then starts the server and a timer that updates the devices every 3 seconds.
     * @param args
     */
    public static void main(String[] args) {

        List<SmartDevice> devices = new java.util.ArrayList<>();
        SmartHomeServer s = new SmartHomeServer(19920);
        Timer timer = new Timer();

        //making a few devices for testing
        SmartLight l = new SmartLight("light 1", 1,true, -1,true,"ffff80",100,true, s);
        SmartLight l2 = new SmartLight("light 2", 2,true, -1,true,"43820180",100,true, s );

        SmartLock lock = new SmartLock("lock 1", 3,true, -1,true, true, s);

        SmartThermostat thermostat = new SmartThermostat("Thermostat 1" , 4,true, 20,true, 20, 20, true, false, s);

        SmartCoffeeMachine coffeeMachine = new SmartCoffeeMachine("Coffee Machine 1", 5, true, -1, true, s);

        SmartGarageDoor garageDoor = new SmartGarageDoor("Garage Door 1", 6, true, -1, true, true, true, true, s);

        SmartSmokeDetector smokeDetector = new SmartSmokeDetector("Smoke Detector 1", 7, true, -1, true, s);

        devices.add(l);
        devices.add(l2);
        devices.add(lock);
        devices.add(thermostat);
        devices.add(coffeeMachine);
        devices.add(garageDoor);
        devices.add(smokeDetector);





        //starting server and adding the devices

        s.newDevice(devices);


        coffeeMachine.setCoffeeBeanLevel(0.5);
        coffeeMachine.setWaterLevel(0.7);


        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(SmartDevice device : devices){
                    device.timerUpdate();
                }

            }
        }, 0, 3000);
    }
}