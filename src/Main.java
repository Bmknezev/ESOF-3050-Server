import smartDevice.SmartLight;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        SmartLight l = new SmartLight("light 1", 1,true, -1,true,0x000000,100,true );
        SmartLight l2 = new SmartLight("light 2", 2,true, -1,true,0x000000,100,true );

        SmartHomeServer s = new SmartHomeServer(19920);
        s.newDevice(l);
        s.newDevice(l2);

        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}