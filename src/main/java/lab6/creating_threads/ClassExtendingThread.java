package lab6.creating_threads;

import java.util.Random;

public class ClassExtendingThread extends Thread {
    public static void main(String[] args) {
        Thread thread = new Thread(new ClassExtendingThread());

        thread.start();
    }

    @Override
    public void run() {
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(3000));
            System.out.println("Woke up!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


