import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    int name;
    private Semaphore leftFork;
    private Semaphore rightFork;
    private Semaphore waiter;

    private int eatCounter = 0;

    private final Option option;

    public Philosopher(int name, Semaphore leftFork, Semaphore rightFork, Option option){
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.option = option;
    }

    public Philosopher(int name, Semaphore leftFork, Semaphore rightFork, Option option, Semaphore waiter){
        this(name, leftFork, rightFork, option);
        this.waiter = waiter;
    }

    @Override
    public void run() {
        super.run();

        if(option != Option.STARVATION){
            try {
                while(true){
                    think();

                    if(waiter != null) waiter.acquire();

                    // acquire forks
                    leftFork.acquire();
                    rightFork.acquire();

                    eat();

                    // release forks
                    leftFork.release();
                    rightFork.release();


                    if(waiter != null) waiter.release();
                }
            } catch (InterruptedException e) {
                System.out.printf("");
            }
        }
        else {
          while(true) {
              think();

              if (leftFork.tryAcquire() && rightFork.tryAcquire()) {
                  eat();
                  // release forks
                  leftFork.release();
                  rightFork.release();
               }
           }
        }

    }

    public void eat(){
        try {
            eatCounter++;
            System.out.println("Philosopher " + this.name + " is eating... | COUNTER : " + eatCounter);
            Thread.sleep((int) Math.random() * 200);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void think(){
        try {
            System.out.println("Philosopher " + this.name + " is thinking...");
            Thread.sleep((int) Math.random() * 200);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
