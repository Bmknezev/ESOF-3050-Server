import java.util.concurrent.BlockingDeque;

public class Test implements Runnable{
//takes the objects from the queue and prints them
    BlockingDeque<Object> q;
    public Test(BlockingDeque<Object> q){
        this.q = q;
    }
    @Override
    public void run() {
        boolean running = true;
        Object o;
        while(running) {
            if(!q.isEmpty()) {
                try {
                    o = q.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(o.toString());
            }
        }
    }
}
