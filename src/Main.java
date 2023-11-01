import java.io.IOException;
import java.util.concurrent.BlockingDeque;

public class Main {
    public static void main(String[] args) {
        BlockingDeque<Object> q = new java.util.concurrent.LinkedBlockingDeque<>();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String msg;
        SmartHomeServer s = new SmartHomeServer(19920, q);
        Test t = new Test(q);
        try {
            s.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(t).start();






    }
}