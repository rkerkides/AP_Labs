package lab6.parallelism_and_threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMath {
    static class RunOnSubarray implements Runnable {
        double[] inputArray, outputArray;
        int firstIndex, count;

        public RunOnSubarray(double[] inputArray, double[] outputArray, int firstIndex, int count) {
            this.inputArray = inputArray;
            this.outputArray = outputArray;
            this.firstIndex = firstIndex;
            this.count = count;
        }

        public void run() {
            int lastIndex = Math.min(inputArray.length, firstIndex + count);
            for (int index = firstIndex; index < lastIndex; ++index) {
                outputArray[index] = calculateExpSlowly(inputArray[index]);
            }
        }

        static double calculateExpSlowly(final double x) {
            double result = 1.;
            double factorial = 1.;
            for (int n = 1; n < 1000; ++n) {
                factorial *= n;
                result += Math.pow(x, n) / factorial;
            }
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int numInputs = 1000005;
        double[] inputs = new double[numInputs];
        double[] outputs = new double[numInputs];
        for (int index = 0; index < numInputs; ++index) {
            inputs[index] = Math.random();
        }

        final int numThreads = 4;
        final int countPerThread = numInputs / numThreads;
        final int remainder = numInputs % numThreads;
        final ExecutorService pool = Executors.newFixedThreadPool(numThreads);

        int start = 0;
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            int count = countPerThread + (threadIndex < remainder ? 1 : 0);
            pool.execute(new RunOnSubarray(inputs, outputs, start, count));
            start += count;
        }

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        double maxResult = Double.MIN_VALUE;
        for (double output : outputs) {
            if (output > maxResult) {
                maxResult = output;
            }
        }
        System.out.println("Maximum result: " + maxResult);
    }
}
