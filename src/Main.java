//-----------------------------------------------------------------
// Main.java
// Group 2
// Description: This program simulates a Smart Home Server where users can add, remove, and adjust various smart devices.
// Created By: Braydon
// Edited By: Braydon, Liam
// Approved By: Braydon, Francisco, Liam
// Variables:
//  - int choice
//
// Error Handling: The program includes error handling for invalid input when selecting options from the menu.
//
//
//-----------------------------------------------------------------

import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**
     * This is the main method for the server. It creates a new server and a few devices for testing.
     * It then starts the server and a timer that updates the devices every 5 seconds.
     * @param args arguments
     */
    public static void main(String[] args) {

        SmartHomeServer s = new SmartHomeServer(19920);


        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //input stream
        Scanner input = new Scanner(System.in);
        System.out.println("type 4 to close server");
        int i = input.nextInt();
        if(i == 4){
            try {
                s.close();
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}