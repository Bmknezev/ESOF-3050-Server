//-----------------------------------------------------------------
// CoffeeAutomationMessage.java
// Group 2
// Description: This class represents a message related to coffee machine automation, storing information about the coffee machine ID, action, and device ID along with the date.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - coffeeMachineID: an integer representing the ID of the coffee machine
//    - coffeeMachineAction: an integer representing the action performed by the coffee machine
//
// Error Handling:
//
//-----------------------------------------------------------------



package messages.automations;

import java.util.Date;

public class CoffeeAutomationMessage extends AbstractAutomationMessage{
    private int coffeeMachineID;
    private int coffeeMachineAction;

    public CoffeeAutomationMessage(int deviceID, int coffeeMachineID, int coffeeMachineAction, Date date) {
        super(deviceID, date, 3);
        this.coffeeMachineID = coffeeMachineID;
        this.coffeeMachineAction = coffeeMachineAction;
    }

    public int getCoffeeMachineID() {
        return coffeeMachineID;
    }

    public int getCoffeeMachineAction() {
        return coffeeMachineAction;
    }
}
