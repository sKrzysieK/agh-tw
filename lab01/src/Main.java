import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
    long startTime = System.currentTimeMillis();
    int runCount = 1000;

    Counter counter = new Counter();

    ArrayList<IncrementingThread> threadList = new ArrayList<>();
    for(int i=0;i < runCount;i++) {
        threadList.add(new IncrementingThread(counter));
    }

    for(int i=0;i < runCount;i++) {
        threadList.get(i).start();
    }
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    System.out.println("Final count: " + counter.getCount());
    System.out.println("Final duration: " + duration + " ms" );

    }
}
