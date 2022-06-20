package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RolColSumTest {

    private final int[][] matrix = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

    @Test
    public void whenSumComputingSerial() {
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        int rowSum1 = sums[0].getRowSum();
        int rowSum2 = sums[1].getRowSum();
        int rowSum3 = sums[2].getRowSum();
        int colSum1 = sums[0].getColSum();
        int colSum2 = sums[1].getColSum();
        int colSum3 = sums[2].getColSum();
        assertThat(rowSum1, is(3));
        assertThat(rowSum2, is(12));
        assertThat(rowSum3, is(21));
        assertThat(colSum1, is(9));
        assertThat(colSum2, is(12));
        assertThat(colSum3, is(15));
    }

    @Test
    public void whenSumComputingAsync() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        int rowSum1 = sums[0].getRowSum();
        int rowSum2 = sums[1].getRowSum();
        int rowSum3 = sums[2].getRowSum();
        int colSum1 = sums[0].getColSum();
        int colSum2 = sums[1].getColSum();
        int colSum3 = sums[2].getColSum();
        assertThat(rowSum1, is(3));
        assertThat(rowSum2, is(12));
        assertThat(rowSum3, is(21));
        assertThat(colSum1, is(9));
        assertThat(colSum2, is(12));
        assertThat(colSum3, is(15));
    }
}