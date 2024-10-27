public class IncrementingThread extends Thread{
    private final Counter counter;

    public IncrementingThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
            counter.increment();
            while(true);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                return;
//            }
    }
}
