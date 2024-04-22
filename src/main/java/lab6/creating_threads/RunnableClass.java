package lab6.creating_threads;

import java.util.List;
import java.util.Random;

public class RunnableClass implements Runnable {
    private String name;

    public RunnableClass(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableClass("Thread 1"));
        Thread thread2 = new Thread(new RunnableClass("Thread 2"));
        Thread thread3 = new Thread(new RunnableClass("Thread 3"));
        Thread thread4 = new Thread(new RunnableClass("Thread 4"));
        Thread thread5 = new Thread(new RunnableClass("Thread 5"));
        Thread thread6 = new Thread(new RunnableClass("Thread 6"));
        Thread thread7 = new Thread(new RunnableClass("Thread 7"));
        Thread thread8 = new Thread(new RunnableClass("Thread 8"));

        List<Thread> threads = List.of(thread, thread2, thread3, thread4, thread5, thread6, thread7, thread8);

        threads.forEach(Thread::start);
        System.out.println("All threads have started!");

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });

        System.out.println("All threads have completed!");
    }

    @Override
    public void run() {
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(3000));
            System.out.println(name + ", woke up!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
