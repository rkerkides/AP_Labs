package lab7.max_finder_continued;

import java.util.concurrent.locks.ReentrantLock;

public class MaxOf1DArrayCopy implements Runnable {

    private final Double[] array;
    private final SharedDouble sharedDouble;

    public MaxOf1DArrayCopy(Double[] array, SharedDouble sharedDouble) {
        this.array = array;
        this.sharedDouble = sharedDouble;
    }

    @Override
    public void run() {
        for (Double aDouble : array) {
            sharedDouble.compare(aDouble);
        }
    }


    public static void main(String[] args) {
        int nRows = 100;
        int nCols = 50;

        Double[][] randArray = new Double[nRows][nCols];
        for(int r=0;r<nRows;r++) {
            for(int c=0;c<nCols;c++) {
                randArray[r][c] = Math.random();
            }
        }

        double maximum = randArray[0][0];

        for (int i = 0; i<nRows; i++) {
            for (int j = 0; j<nCols; j++) {
                if (randArray[i][j] > maximum) {
                    maximum = randArray[i][j];
                }
            }
        }

        System.out.println(maximum);

        SharedDouble sharedDouble = new SharedDouble();
        sharedDouble.setD(0.0);

        Thread[] threads = new Thread[nRows];

        for (int i = 0; i <nRows; i++) {
            var max = new MaxOf1DArrayCopy(randArray[i], sharedDouble);
            Thread thread = new Thread(max);
            thread.start();
            threads[i] = thread;
        }

        for (int i = 0; i <nRows; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        System.out.println(sharedDouble.getD());
    }
}
