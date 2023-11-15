package smartDevice;

public class SmartCoffeeMachine extends SmartDevice{
    private boolean cupStatus; //true if cup is in place, false if cup is not in place
    private int waterLevel; //0-100
    private int coffeeLevel; //0-100
    private int timer; //0-60
    private String coffeeType; //lets user input type of coffee
    private boolean readyToBrew; //true if ready to brew, false if not ready to brew
    private boolean brewing; //true if brewing, false if not brewing

    public SmartCoffeeMachine(String name, int id, boolean connectionStatus, int battery, boolean status){
        super(id, name, connectionStatus, battery, status);
        this.cupStatus = false;
        this.waterLevel = 0;
        this.coffeeLevel = 0;
        this.timer = 0;
        this.coffeeType = "none";
        this.readyToBrew = false;
        this.brewing = false;
    }

    @Override
    public void update(String[] s) {
        System.out.println("Updating Smart Coffee Machine");
        setCupStatus(Boolean.parseBoolean(s[0]));
        setWaterLevel(Integer.parseInt(s[1]));
        setCoffeeLevel(Integer.parseInt(s[2]));
        setTimer(Integer.parseInt(s[3]));
        setCoffeeType(s[4]);
        setReadyToBrew(Boolean.parseBoolean(s[5]));
        setBrewing(Boolean.parseBoolean(s[6]));
    }

    @Override
    public String getDetails() {
        return super.getDeviceID() + "|" + super.getName() + "|" + getCupStatus() + "|" + getWaterLevel() + "|" + getCoffeeLevel() + "|" + getCoffeeType() + "|" + getReadyToBrew() + "|" + getBrewing();
    }

    public String toString(){
        return super.getName() + "|" + "Smart Coffee Machine" + "|" + super.getDeviceID();
    }
    public void setCupStatus(boolean cupStatus){
        this.cupStatus = cupStatus;
    }

    public void setWaterLevel(int waterLevel){
        this.waterLevel = waterLevel;
    }

    public void setCoffeeLevel(int coffeeLevel){
        this.coffeeLevel = coffeeLevel;
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

    public int getWaterLevel(){
        return waterLevel;
    }

    public int getCoffeeLevel(){
        return coffeeLevel;
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


}
