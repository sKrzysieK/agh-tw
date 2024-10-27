public class Counter{
    private int count = 0;

    public synchronized void increment() {
        count++;
        System.out.println("Incremented: " + count);
    }

    public synchronized void decrement() {
        count--;
        System.out.println("Decremented: " + count);
    }

    public synchronized int getCount() {
        return count;
    }
}
