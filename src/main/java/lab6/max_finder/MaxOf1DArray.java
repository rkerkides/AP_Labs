package lab6.max_finder;

public class MaxOf1DArray implements Runnable {

    private Double[] array;
    public Double[] resultArray;
    private int position;

    public MaxOf1DArray(Double[] array, Double[] resultArray, int position) {
        this.array = array;
        this.resultArray = resultArray;
        this.position = position;
    }

    @Override
    public void run() {
        double maximum = array[0];

        for (Double aDouble : array) {
            if (aDouble > maximum) {
                maximum = aDouble;
            }
        }

        resultArray[position] = maximum;
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

        Double [] resultsArray = new Double[nRows];

        Thread[] threads = new Thread[nRows];

        for (int i = 0; i <nRows; i++) {
            var max = new MaxOf1DArray(randArray[i], resultsArray, i);
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

        var max = new MaxOf1DArray(resultsArray, new Double[1], 0);
        Thread maxThread = new Thread(max);
        maxThread.start();
        try {
            maxThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        System.out.println(max.resultArray[0]);
    }
}
