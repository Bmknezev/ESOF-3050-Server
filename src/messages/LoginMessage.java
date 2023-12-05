//-----------------------------------------------------------------
// LoginMessage.java
// Group 2
// Description: This class represents a message related to user login credentials, storing information about the username, password, login status, and whether the user is an admin or not.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - username: a String representing the username for login
//    - password: a String representing the password for login
//    - loginStatus: a boolean indicating the status of the login (true/false)
//    - admin: a boolean indicating whether the user is an admin or not (true/false)
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public class LoginMessage extends AbstractMessage{
    private String username;
    private String password;
    private boolean loginStatus;

    private boolean admin;

    public LoginMessage(String username, String password) {
        super(5);
        this.username = username;
        this.password = password;
        loginStatus = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }
}
