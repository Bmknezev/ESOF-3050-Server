package smartDevice;

public class SmartGarageDoor extends SmartDevice{
    private boolean safteySwitch; //true if object is detected under door, false if no object is detected
    private boolean doorStatus; //true if door is open, false if door is closed
    private boolean moving; //true if door is moving, false if door is not moving
    private boolean usePassword; //true if password is needed to open door, false if password is not needed
    private int password; //password to open door
    private int passwordAttempts; //number of times password has been entered incorrectly

    public SmartGarageDoor(String name, int id, boolean connectionStatus, int battery, boolean status, boolean safteySwitch, boolean doorStatus, boolean moving){
        super(id, name,connectionStatus, battery, status);
        this.safteySwitch = safteySwitch;
        this.doorStatus = doorStatus;
        this.moving = moving;
        this.passwordAttempts = 0;
    }

    public void setSafteySwitch(boolean safteySwitch){
        this.safteySwitch = safteySwitch;
    }

    public void setDoorStatus(boolean doorStatus){
        this.doorStatus = doorStatus;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    public void setUsePassword(boolean usePassword, int password){
        this.usePassword = usePassword;
        this.setPassword(password);
    }

    public void setPassword(int password){
        this.password = password;
    }

    public boolean getSafteySwitch(){
        return safteySwitch;
    }

    public boolean getDoorStatus(){
        return doorStatus;
    }

    public boolean getMoving(){
        return moving;
    }

    public boolean getUsePassword(){
        return usePassword;
    }

    public int getPassword(){
        return password;
    }

    public Boolean authenticatePassword(int password){
        if (this.password == password){
            passwordAttempts = 0;
            return true;
        }
        else{
            passwordAttempts++;
            if(passwordAttempts >= 3){
                this.alertOwner();
            }
            return false;
        }
    }

    public void alertOwner(){
        System.out.println("ALERT: Someone is trying to break into your house!");
    }

    @Override
    public void update(String[] s) {
        System.out.println("Updating Smart Garage Door");
        setSafteySwitch(Boolean.parseBoolean(s[0]));
        setDoorStatus(Boolean.parseBoolean(s[1]));
        setMoving(Boolean.parseBoolean(s[2]));
        setUsePassword(Boolean.parseBoolean(s[3]), Integer.parseInt(s[4]));
    }

    @Override
    public String getDetails() {
        return super.getDeviceID() + "|" + super.getName() + "|" + safteySwitch + "|" + doorStatus + "|" + moving + "|" + usePassword + "|" + password;
    }

    public String toString(){
        return super.getName() + "|" + "Smart Garage Door" + "|" + super.getDeviceID();
    }
}
