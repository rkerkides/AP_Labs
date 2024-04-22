package lab7.atomic_counter;

public class CounterExampleOriginal {
    public static class MyCounter {
        // This object stores the current value of the counter
        // We will pass a reference to it to all threads
        // Get and set methods allow other objects to change count
        private int count = 0;
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
    }
    public static class Counter extends Thread {
        // This is the Thread object that will be doing the counting.
        // We pass it a reference to a counter object and how many increments.
        private volatile MyCounter count;
        private int n;
        public Counter(MyCounter count,int n) {
            this.count = count;
            this.n = n;
        }
        // The run method does the counting
        public void run() {
            for(int i=0;i<n;i++) {
                int temp = count.getCount();
                temp++;
                count.setCount(temp);
            }
        }
    }
    public static void main(String[] args) {
        // Create a new MyCounter object
        MyCounter count = new MyCounter();
        int nCounters = 100;
        Counter[] c = new Counter[nCounters];
        for(int i=0;i<nCounters;i++) {
            c[i] = new Counter(count,1000);
            c[i].start();
        }
        try {
            for(int i=0;i<nCounters;i++) {
                c[i].join();
            }
        }catch(InterruptedException e) {
            //Do nothing
        }
        // Display the counter value
        System.out.println(count.getCount());
    }
}