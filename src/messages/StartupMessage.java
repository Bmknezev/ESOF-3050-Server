//-----------------------------------------------------------------
// StartupMessage.java
// Group 2
// Description: This class represents a startup message, containing information about the client ID during initialization.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//    - clientID: an integer representing the client's identification
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public class StartupMessage extends AbstractMessage{
    private int clientID;

    public StartupMessage(int clientID){
        //used by both server and client
        super(3);
        this.clientID = clientID;
    }

    public int getClientID(){
        return clientID;
    }

}
