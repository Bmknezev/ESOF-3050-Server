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