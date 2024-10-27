import java.util.ArrayList;
import java.util.List;

class Producer extends Thread {
    private final Buffer _buf;

    public Producer(Buffer buffer) {
        _buf = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            _buf.put(i);

            // 3
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

class Consumer extends Thread {
    private final Buffer _buf;

    public Consumer(Buffer buffer) {
        _buf = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            _buf.get();

            // 3
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

class Buffer {
    int buffer = 0;
    private boolean transfer = true;
    public synchronized void put(int i) {
        while (!transfer){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted!");
            }
        }
        transfer = false;
        System.out.println("Setting buffer to: " + i);
        buffer = i;
        notifyAll();
    }

    public synchronized int get() {
        while(transfer){
            try {
                wait();
            }catch (InterruptedException e){
               Thread.currentThread().interrupt();
                System.out.println("Thread interrupted!");
            }
        }
        transfer = true;
        System.out.println(buffer);
        notifyAll();
        return buffer;
    }
}

public class PKmon {
    public static void main(String[] args) {
        Buffer sharedBuffer = new Buffer();
        // zad-3

        //1
//
//        Consumer consumerThread = new Consumer(buffer);
//        Producer producerThread = new Producer(buffer);
//
//        producerThread.start();
//        consumerThread.start();

        //2

        // n1 > n2 -> zakleszczenie
//         int n1 = 5;
//         int n2 = 3;

        // n1 < n2 -> zakleszczenie
//         int n1 = 3;
//         int n2 = 5;

        // n1 = n2 -> ok
//        int n1 = 3;
//        int n2 = 3;
//
//
//        for(int i = 0; i < n1; i++){
//            Producer producerThread = new Producer(sharedBuffer);
//            producerThread.start();
//        }
//
//        for(int i = 0; i < n2; i++){
//            Consumer consumerThread = new Consumer(sharedBuffer);
//            consumerThread.start();
//        }
    }
}


