package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = init(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSum(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = init(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getTask(matrix, i).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> getSum(matrix, index));
    }

    private static Sums getSum(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }
        return new Sums(rowSum, colSum);
    }

    private static Sums[] init(int length) {
        Sums[] sums = new Sums[length];
        for (int i = 0; i < length; i++) {
            sums[i] = new Sums(0, 0);
        }
        return sums;
    }

}