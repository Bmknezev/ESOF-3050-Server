package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.server.CoffeeMessage;
import messages.BrewCoffeeMessage;

public class SmartCoffeeMachine extends SmartDevice{
    private boolean cupStatus; //true if cup is in place, false if cup is not in place
    private double waterLevel; //0-1
    private double coffeeBeanLevel; //0-1
    private int timer; //0-60
    private String coffeeType; //lets user input type of coffee
    private boolean readyToBrew; //true if ready to brew, false if not ready to brew
    private boolean brewing; //true if brewing, false if not brewing
    private double coffeeLevel; //amount of coffee in pot

    /**
     * This is the constructor for the SmartCoffeeMachine class.
     * @param name device name
     * @param id device id
     * @param connectionStatus connection status
     * @param battery battery level
     * @param status device status, online or offline
     * @param server server
     */
    public SmartCoffeeMachine(String name, int id, boolean connectionStatus, int battery, boolean status, AbstractServer server){
        super(id, name, connectionStatus, battery, status, server);
        this.cupStatus = false;
        this.waterLevel = 0;
        this.coffeeBeanLevel = 0;
        this.timer = 0;
        this.coffeeType = "none";
        this.readyToBrew = true;
        this.brewing = false;
        this.coffeeLevel = 0;
    }


    /**
     * This method updates the device's status.
     * @param msg message from server
     */
    public void update(AbstractDeviceMessage msg) {

        if(msg instanceof CoffeeMessage) {
            CoffeeMessage message = (CoffeeMessage) msg;
            super.update(msg);
            this.cupStatus = message.getCupStatus();
            this.waterLevel = message.getWaterLevel();
            this.coffeeBeanLevel = message.getCoffeeBeanLevel();
            this.coffeeType = message.getCoffeeType();
            this.readyToBrew = message.getReadyToBrew();
            this.coffeeLevel = message.getCoffeeLevel();
        }else if(msg instanceof BrewCoffeeMessage){
            BrewCoffeeMessage message = (BrewCoffeeMessage) msg;
            brew(message);
        }
    }

    /**
     * This method runs when the client sends a BrewCoffeeMessage.
     * @param msg message from server
     */
    private void brew(BrewCoffeeMessage msg) {
        int tmpsize = 0;
        int tmpstrength = 0;
        System.out.println("size: " + msg.getSize() + " strength: " + msg.getStrength());
        switch (msg.getSize()) {
            case "Small":
                if(msg.getWaterLevel() < 0.1 || 1 - msg.getCoffeeLevel() < 0.1)
                    return;
                tmpsize = 1;
                break;
            case "Medium":
                if(msg.getWaterLevel() < 0.2 || 1 - msg.getCoffeeLevel() < 0.2)
                    return;
                tmpsize = 2;
                break;
            case "Large":
                if(msg.getWaterLevel() < 0.3 || 1 - msg.getCoffeeLevel() < 0.3)
                    return;
                tmpsize = 3;
                break;
        }
        switch (msg.getStrength()) {
            case "Weak":
                if(msg.getCoffeeBeanLevel() < 0.1)
                    return;
                tmpstrength = 1;
                break;
            case "Medium Strength":
                if(msg.getCoffeeBeanLevel() < 0.2)
                    return;
                tmpstrength = 2;
                break;
            case "Strong":
                if(msg.getCoffeeBeanLevel() < 0.3)
                    return;
                tmpstrength = 3;
                break;
        }

        if(tmpsize != 0 || tmpstrength != 0){
            setWaterLevel(getWaterLevel() - (tmpsize * 0.1));
            setCoffeeBeanLevel(getCoffeeBeanLevel() - (tmpstrength * 0.1));
            setReadyToBrew(false);
            setBrewing(true);
            server.sendToAllClients(new CoffeeMessage(getDeviceID(),getName(), getCupStatus(), getWaterLevel(), getCoffeeBeanLevel(), getCoffeeType(), getReadyToBrew(), getCoffeeLevel()));
        }else{
            return;
        }

            try {
                for (int i = 0; i < tmpsize; i++) {
                    for (int j = 0; j < tmpstrength; j++) {
                        System.out.println("Brewing Coffee...");
                        Thread.sleep(1000);
                        setCoffeeLevel(getCoffeeLevel() + 0.1);
                        server.sendToAllClients(new CoffeeMessage(getDeviceID(),getName(), getCupStatus(), getWaterLevel(), getCoffeeBeanLevel(), getCoffeeType(), getReadyToBrew(), getCoffeeLevel()));
                    }
                }
                setBrewing(false);
                if(waterLevel > 0 && coffeeBeanLevel > 0)
                    setReadyToBrew(true);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    private void setCoffeeLevel(double coffee) {
        this.coffeeLevel = coffee;
    }

    private double getCoffeeLevel() {
        return coffeeLevel;
    }

    public void setCupStatus(boolean cupStatus){
        this.cupStatus = cupStatus;
    }

    public void setWaterLevel(double waterLevel){
        this.waterLevel = waterLevel;
    }

    public void setCoffeeBeanLevel(double coffeeLevel){
        this.coffeeBeanLevel = coffeeLevel;
    }

    public void setTimer(int timer){
        this.timer = timer;
    }

    public void setCoffeeType(String coffeeType){
        this.coffeeType = coffeeType;
    }

    public void setReadyToBrew(boolean readyToBrew){
        this.readyToBrew = readyToBrew;
    }

    public void setBrewing(boolean brewing){
        this.brewing = brewing;
    }

    public boolean getCupStatus(){
        return cupStatus;
    }

    public double getWaterLevel(){
        return waterLevel;
    }

    public double getCoffeeBeanLevel(){
        return coffeeBeanLevel;
    }

    public int getTimer(){
        return timer;
    }

    public String getCoffeeType(){
        return coffeeType;
    }

    public boolean getReadyToBrew(){
        return readyToBrew;
    }

    public boolean getBrewing(){
        return brewing;
    }

    @Override
    public Object PrepareMessage() {
        return new CoffeeMessage(getDeviceID(), getName(), getCupStatus(), getWaterLevel(), getCoffeeBeanLevel(), getCoffeeType(), getReadyToBrew(), getCoffeeLevel());
    }

    @Override
    public String getType() {
        return "Smart Coffee Machine";
    }

    @Override
    public void timerUpdate(){
        //this method is called every second by the timer
        //it is used to update the device's status
        //this is where the device would check if it is still connected to the server
        //and update its battery level
        //this is run on the JavaFX thread
        //this method is empty because the coffee machine does not need to update anything
        server.sendToAllClients(PrepareMessage());
    }
}
