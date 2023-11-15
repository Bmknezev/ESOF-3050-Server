package smartDevice;

import com.lloseng.ocsf.server.AbstractServer;
import messages.AbstractDeviceMessage;
import messages.CoffeeMessage;

public class SmartCoffeeMachine extends SmartDevice{
    private boolean cupStatus; //true if cup is in place, false if cup is not in place
    private double waterLevel; //0-1
    private double coffeeBeanLevel; //0-1
    private int timer; //0-60
    private String coffeeType; //lets user input type of coffee
    private boolean readyToBrew; //true if ready to brew, false if not ready to brew
    private boolean brewing; //true if brewing, false if not brewing
    private double coffeeLevel; //amount of coffee in pot
    private AbstractServer server;

    public SmartCoffeeMachine(String name, int id, boolean connectionStatus, int battery, boolean status){
        super(id, name, connectionStatus, battery, status);
        this.cupStatus = false;
        this.waterLevel = 0;
        this.coffeeBeanLevel = 0;
        this.timer = 0;
        this.coffeeType = "none";
        this.readyToBrew = false;
        this.brewing = false;
        this.coffeeLevel = 0;
    }


    public void update(AbstractDeviceMessage msg) {
        CoffeeMessage message = (CoffeeMessage) msg;
        super.update(msg);
        this.cupStatus = message.getCupStatus();
        this.waterLevel = message.getWaterLevel();
        this.coffeeBeanLevel = message.getCoffeeBeanLevel();
        this.timer = message.getTimer();
        this.coffeeType = message.getCoffeeType();
        this.readyToBrew = message.getReadyToBrew();
        this.brewing = message.getBrewing();
        this.coffeeLevel = message.getCoffeeLevel();
    }

    private void brew(String size, String strength){
        int tmpsize = 0;
        int tmpstrength = 0;
        System.out.println(size + " " + strength);
        switch (size){
            case "Small":
                tmpsize = 1;
                setWaterLevel(getWaterLevel()-0.1);
                break;
            case "Medium":
                tmpsize = 2;
                setWaterLevel(getWaterLevel()-0.2);
                break;
            case "Large":
                tmpsize = 3;
                setWaterLevel(getWaterLevel()-0.3);
                break;
        }
        switch (strength){
            case "Weak":
                tmpstrength = 1;
                setCoffeeBeanLevel(getCoffeeBeanLevel()-0.1);
                break;
            case "Medium Strength":
                tmpstrength = 2;
                setCoffeeBeanLevel(getCoffeeBeanLevel()-0.2);
                break;
            case "Strong":
                tmpstrength = 3;
                setCoffeeBeanLevel(getCoffeeBeanLevel()-0.3);
                break;
        }

        try{
            for(int i = 0; i < tmpsize; i++){
                for(int j = 0; j < tmpstrength; j++){
                    System.out.println("Brewing Coffee...");
                    Thread.sleep(1000);
                    setCoffeeLevel(getCoffeeLevel()+0.1);
                    //server.sendToAllClients(0 + "|" + getDetails() + "|" + "coffeeLevel|" + getCoffeeLevel());
                }
            }

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

    public void setServer(AbstractServer server) {
        this.server = server;
    }

    @Override
    public Object PrepareMessage() {
        return new CoffeeMessage(true, getDeviceID(), getName(), getConnectionStatus(), getBattery(), getStatus(), getCupStatus(), getWaterLevel(), getCoffeeBeanLevel(), getTimer(), getCoffeeType(), getReadyToBrew(), getBrewing(), getCoffeeLevel());
    }
}
