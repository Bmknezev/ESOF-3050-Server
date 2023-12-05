//-----------------------------------------------------------------
// AbstractMessage.java
// Group 2
// Description: This abstract class serves as a base for messages, providing a type indicator for different message types.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages;

public abstract class AbstractMessage implements java.io.Serializable{
   private int type; //1 for requesting device details, 2 for receiving device details, 3 for new client message


    public AbstractMessage(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
