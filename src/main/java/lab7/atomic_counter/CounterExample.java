package lab7.atomic_counter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
public class CounterExample {
	public static class MyCounter {
		private AtomicInteger count = new AtomicInteger(0);
		public void increment() {
			count.getAndIncrement();
		}
		public int getCount() {
			return count.get();
		}
	}
	public static class Counter extends Thread {
		private MyCounter count;
		private int n;
		public Counter(MyCounter count,int n) {
			this.count = count;
			this.n = n;
		}
		public void run() {
			for(int i=0;i<n;i++) {
				count.increment();
			}
		}
	}
	public static void main(String[] args) {
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
		System.out.println(count.getCount());

		int incrementsPerThread = 20000;

		System.out.println("Using AtomicInteger:");
		measurePerformance(new MyCounter(), 4, incrementsPerThread);  // Fewer threads
		measurePerformance(new MyCounter(), 50, incrementsPerThread); // More threads

	}

	public static void measurePerformance(MyCounter counter, int nThreads, int incrementsPerThread) {
		Counter[] threads = new Counter[nThreads];

		long startTime = System.nanoTime();
		for (int i = 0; i < nThreads; i++) {
			threads[i] = new Counter(counter, incrementsPerThread);
			threads[i].start();
		}
		try {
			for (int i = 0; i < nThreads; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Set the interrupt flag again
			System.out.println("Thread was interrupted.");
		}
		long endTime = System.nanoTime();

		long duration = endTime - startTime;
		System.out.println("Count: " + counter.getCount() + " | Time taken: " + duration / 1_000_000 + " ms");
	}

}