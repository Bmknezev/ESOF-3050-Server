import smartDevice.SmartLight;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;

public class Main {
    public static void main(String[] args) {
        BlockingDeque<Object> q = new java.util.concurrent.LinkedBlockingDeque<>();

        SmartLight l = new SmartLight("light 1", 1,true, -1,true,0x000000,100,false );

        SmartHomeServer s = new SmartHomeServer(19920, q);
        s.newDevice(l);

        Test t = new Test(q);
        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(t).start();
    }
}