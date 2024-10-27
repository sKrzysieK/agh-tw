public class DecrementingThread extends Thread{
    private final Counter counter;

    public DecrementingThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
            counter.decrement();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                return;
//            }
    }
}
