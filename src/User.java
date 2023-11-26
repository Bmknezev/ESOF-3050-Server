import messages.UserListMessage;

public class User {
    private String username;
    private String password;
    private boolean admin;
    private int userID;

    public User(int id, String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.userID = id;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public boolean getAdmin(){
        return admin;
    }

    public int getUserID() {
        return userID;
    }

    public UserListMessage prepareMessage(){
        return new UserListMessage(userID, username, password, admin, false);
    }

    public void update(UserListMessage msg){
        this.username = msg.getUsername();
        this.password = msg.getPassword();
        this.admin = msg.getAdmin();
    }

}
