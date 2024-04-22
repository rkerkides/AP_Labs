package lab7.max_finder_continued;

import java.util.concurrent.locks.ReentrantLock;

public class SharedDouble {
    private Double d;
    private final ReentrantLock lock = new ReentrantLock();
    public Double getD() {
        return d;
    }
    public void setD(Double d) {
        this.d = d;
    }

    public void compare(Double a) {
        lock.lock();
        try {
            if (a > d) {
                Thread.sleep(1);
                d = a;
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}