import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        Option option = Option.WAITER;
        int philosopherCount = 5;
        List<Semaphore> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();

        Semaphore waiter = new Semaphore(philosopherCount - 1);

        for(int i = 0;i < philosopherCount; i++){
            forks.add( new Semaphore(1) );
        }

        for(int i = 0;i < philosopherCount; i++){
            Philosopher philosopher = switch (option){
                case ASYMMETRIC -> new Philosopher(
                            i,
                            forks.get(i % 2 == 0 ? i : (i+1) % philosopherCount),
                            forks.get(i % 2 == 0 ? (i+1) % philosopherCount : i),
                            option
                    );
                case WAITER ->  new Philosopher(
                        i,
                        forks.get(i),
                        forks.get((i+1) % philosopherCount),
                        option,
                        waiter
                );
                default -> new Philosopher(
                        i,
                        forks.get(i),
                        forks.get((i+1) % philosopherCount),
                        option
                );
            };

            philosophers.add(philosopher);

        }

        for(int i = 0;i < philosopherCount; i++){
            philosophers.get(i).start();
        }
    }
}
