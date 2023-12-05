//-----------------------------------------------------------------
// BrewCoffeeMessage.java
// Group 2
// Description: This class represents a message related to brewing coffee, storing information about the device name, ID, size, strength, water level, coffee bean level, brewed coffee level, and temperature for brewing.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - size: a String representing the size of the brewed coffee
//    - strength: a String indicating the strength of the brewed coffee
//    - waterLevel: a double representing the water level for brewing
//    - coffeeBeanLevel: a double representing the level of coffee beans for brewing
//    - coffeeLevel: a double representing the level of brewed coffee
//    - temperature: a String representing the temperature of the brewed coffee
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public class BrewCoffeeMessage extends AbstractDeviceMessage{

    private String size;
    private String strength;
    private double waterLevel;
    private double coffeeBeanLevel;
    private double coffeeLevel;
    private String temperature;

    public BrewCoffeeMessage(String name, int id, String size, String strength, double water, double beans, double coffee, String temp) {
        super(name, id);
        this.size = size;
        this.strength = strength;
        this.waterLevel = water;
        this.coffeeBeanLevel = beans;
        this.coffeeLevel = coffee;
        this.temperature = temp;
    }

    public String getSize() {
        return size;
    }

    public String getStrength() {
        return strength;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public double getCoffeeBeanLevel() {
        return coffeeBeanLevel;
    }

    public double getCoffeeLevel() {
        return coffeeLevel;
    }

    public String getTemperature() {
        return temperature;
    }
}
