import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PKMon2 {
    private static final Object END_OF_STREAM = new Object();

    static class Producer extends Thread {
        private BlockingQueue<Object> outQueue;

        public Producer(BlockingQueue<Object> outQueue) {
            this.outQueue = outQueue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; ++i) {
                    System.out.println(String.format("%s produced %d", Thread.currentThread().getName(), i));  // Print message when data is produced
                    outQueue.put(i);
                    Thread.sleep((int)(Math.random() * 1000));
                }
                outQueue.put(END_OF_STREAM);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private BlockingQueue<Object> inQueue;

        public Consumer(BlockingQueue<Object> inQueue) {
            this.inQueue = inQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object data = inQueue.take();
                    if (data == END_OF_STREAM) {
                        break;
                    }
                    System.out.println(String.format("%s consumed %s", Thread.currentThread().getName(), data));
                    Thread.sleep((int)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class PipelineProcessor extends Thread {
        private BlockingQueue<Object> inQueue, outQueue;

        public PipelineProcessor(BlockingQueue<Object> inQueue, BlockingQueue<Object> outQueue) {
            this.inQueue = inQueue;
            this.outQueue = outQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object data = inQueue.take();
                    if (data == END_OF_STREAM) {
                        outQueue.put(END_OF_STREAM);
                        break;
                    }
                    System.out.println(String.format("%s received %d", Thread.currentThread().getName(), (int) data));  // Print message when data is received
                    int newData = ((int) data + 10) % 100;
                    outQueue.put(newData);
                    System.out.println(String.format("%s passed %d", Thread.currentThread().getName(), newData));  // Print message when new data is passed
                    Thread.sleep((int)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int bufSize = 5;
        BlockingQueue<Object>[] buffers = new BlockingQueue[bufSize + 1];
        for(int i = 0; i <= bufSize; i++){
            buffers[i] = new LinkedBlockingQueue<>();
        }

        new Producer(buffers[0]).start();

        for(int i = 0; i < bufSize; i++){
            new PipelineProcessor(buffers[i], buffers[i+1]).start();
        }

        new Consumer(buffers[bufSize]).start();
    }
}