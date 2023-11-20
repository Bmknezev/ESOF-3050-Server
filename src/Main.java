import smartDevice.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    /**
     * This is the main method for the server. It creates a new server and a few devices for testing.
     * It then starts the server and a timer that updates the devices every 5 seconds.
     * @param args arguments
     */
    public static void main(String[] args) {

        List<SmartDevice> devices = new java.util.ArrayList<>();
        SmartHomeServer s = new SmartHomeServer(19920);
        Timer timer = new Timer();


        //making a few devices for testing
        SmartLight l = new SmartLight("light 1", 1, true, -1, true, "ffff80", 100, true, s);
        SmartLight l2 = new SmartLight("light 2", 2, true, -1, true, "43820180", 100, true, s);

        SmartLock lock = new SmartLock("lock 1", 3, true, -1, true, true, "1234", s);

        SmartThermostat thermostat = new SmartThermostat("Thermostat 1", 4, true, 20, true, 20, 20, true, false, s);

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
        for (SmartDevice device : devices) {
            s.newDevice(device);
        }


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
                for (SmartDevice device : devices) {
                    device.timerUpdate();
                }

            }
        }, 0, 5000);


        //dev menu stuff

        //input stream
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Smart Home Server Dev Menu");
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Add a new device");
            System.out.println("2. Remove a device");
            System.out.println("3. adjust a device");
            System.out.println("4. close server");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Please select a device type:");
                    System.out.println("1. Smart Light");
                    System.out.println("2. Smart Lock");
                    System.out.println("3. Smart Thermostat");
                    System.out.println("4. Smart Coffee Machine");
                    System.out.println("5. Smart Garage Door");
                    System.out.println("6. Smart Smoke Detector");
                    choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Please enter the light name:");
                            String name = input.next();
                            System.out.println("Please enter the light colour hex code:");
                            String colour = input.next();
                            System.out.println("Please enter the light brightness:");
                            int brightness = input.nextInt();
                            SmartLight light1 = new SmartLight(name, devices.size() + 1, true, -1, true, colour, brightness, false, s);
                            devices.add(light1);
                            s.newDevice(light1);
                            break;
                        case 2:
                            System.out.println("Please enter the lock name:");
                            name = input.next();
                            System.out.println("Please enter the lock code:");
                            String code = input.next();
                            SmartLock lock1 = new SmartLock(name, devices.size() + 1, true, -1, true, true, code, s);
                            devices.add(lock1);
                            s.newDevice(lock1);
                            break;
                        case 3:
                            System.out.println("Please enter the thermostat name:");
                            name = input.next();
                            System.out.println("Please enter the thermostat temperature:");
                            int temperature = input.nextInt();
                            SmartThermostat thermostat1 = new SmartThermostat(name, devices.size() + 1, true, -1, true, temperature, temperature, true, false, s);
                            devices.add(thermostat1);
                            s.newDevice(thermostat1);
                            break;
                        case 4:
                            System.out.println("Please enter the coffee machine name:");
                            name = input.next();
                            SmartCoffeeMachine coffeeMachine1 = new SmartCoffeeMachine(name, devices.size() + 1, true, -1, true, s);
                            devices.add(coffeeMachine1);
                            s.newDevice(coffeeMachine1);
                            break;
                        case 5:
                            System.out.println("Please enter the garage door name:");
                            name = input.next();
                            SmartGarageDoor garageDoor1 = new SmartGarageDoor(name, devices.size() + 1, true, -1, true, true, true, true, s);
                            devices.add(garageDoor1);
                            s.newDevice(garageDoor1);
                            break;
                        case 6:
                            System.out.println("Please enter the smoke detector name:");
                            name = input.next();
                            SmartSmokeDetector smokeDetector1 = new SmartSmokeDetector(name, devices.size() + 1, true, -1, true, s);
                            devices.add(smokeDetector1);
                            s.newDevice(smokeDetector1);
                            break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }

                case 2:
                    System.out.println("Please select a device to remove:");
                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println(i + ": " + devices.get(i).getName());
                    }
                    choice = input.nextInt();
                    devices.remove(choice);
                case 3:
                    System.out.println("Please select a device to adjust:");
                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println(i + ": " + devices.get(i).getName());
                    }
                    int device = input.nextInt();
                    System.out.println("Please select a value to adjust:");
                    switch (devices.get(device).getType()) {
                        case "Smart Light":
                            //light
                            System.out.println("1. Name");
                            System.out.println("2. Colour");
                            System.out.println("3. Brightness");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new colour hex code:");
                                    ((SmartLight) devices.get(device)).setColour(input.next());
                                    break;
                                case 3:
                                    System.out.println("Please enter the new brightness:");
                                    ((SmartLight) devices.get(device)).setBrightness(Integer.parseInt(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                            break;
                        case "Smart Lock":
                            //lock
                            System.out.println("1. Name");
                            System.out.println("2. Code");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new code:");
                                    ((SmartLock) devices.get(choice)).setPassword(Integer.parseInt(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                            break;
                        case "Smart Thermostat":
                            //thermostat
                            System.out.println("1. Name");
                            System.out.println("2. Temperature");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new temperature:");
                                    ((SmartThermostat) devices.get(choice)).setTemperature(Integer.parseInt(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                            break;
                        case "Smart Coffee Machine":
                            //coffee machine
                            System.out.println("1. Name");
                            System.out.println("2. Coffee Bean Level");
                            System.out.println("3. Water Level");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new coffee bean level:");
                                    ((SmartCoffeeMachine) devices.get(choice)).setCoffeeBeanLevel(Double.parseDouble(input.next()));
                                    break;
                                case 3:
                                    System.out.println("Please enter the new water level:");
                                    ((SmartCoffeeMachine) devices.get(choice)).setWaterLevel(Double.parseDouble(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                            break;
                        case "Smart Garage Door":
                            //garage door
                            System.out.println("1. Name");
                            System.out.println("2. door Status");
                            System.out.println("3. use PIN");
                            System.out.println("4. PIN");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new door status(open/closed):");
                                    boolean doorStatus = input.next().equals("open");
                                    ((SmartGarageDoor) devices.get(choice)).setDoorStatus(doorStatus);
                                    break;
                                case 3:
                                    System.out.println("Please enter the new use pin status(true/false):");
                                    boolean usepin = Boolean.parseBoolean(input.next());
                                    System.out.println("Please enter the new pin:");
                                    ((SmartGarageDoor) devices.get(choice)).setUsePassword(usepin, Integer.parseInt(input.next()));
                                    break;
                                case 4:
                                    System.out.println("Please enter the new pin:");
                                    ((SmartGarageDoor) devices.get(choice)).setPassword(Integer.parseInt(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                            break;
                        case "Smart Smoke Detector":
                            //smoke detector
                            System.out.println("1. Name");
                            System.out.println("2. Smoke");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1:
                                    System.out.println("Please enter the new name:");
                                    devices.get(device).setName(input.next());
                                    break;
                                case 2:
                                    System.out.println("Please enter the new smoke status:");
                                    //((SmartSmokeDetector)devices.get(choice)).setSmoke(Boolean.parseBoolean(input.next()));
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                    }
                    break;

                case 4:
                    System.exit(0);
            }
        }
    }
    }