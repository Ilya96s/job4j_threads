package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.*;

/**
 * CompletableFuture
 *
 * @author Ilya Kaltygin
 */
class RolColSumTest {

    @Test
    void whenSum() {
        int[][] matrix = new int[4][4];
        int count = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = count;
                count++;
            }
        }
        RolColSum.Sums sums1 = new RolColSum.Sums(10, 28);
        RolColSum.Sums sums2 = new RolColSum.Sums(26, 32);
        RolColSum.Sums sums3 = new RolColSum.Sums(42, 36);
        RolColSum.Sums sums4 = new RolColSum.Sums(58, 40);
        RolColSum.Sums[] expected = RolColSum.sum(matrix);
        assertThat(expected[0]).isEqualTo(sums1);
        assertThat(expected[1]).isEqualTo(sums2);
        assertThat(expected[2]).isEqualTo(sums3);
        assertThat(expected[3]).isEqualTo(sums4);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[4][4];
        int count = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = count;
                count++;
            }
        }
        RolColSum.Sums sums1 = new RolColSum.Sums(10, 28);
        RolColSum.Sums sums2 = new RolColSum.Sums(26, 32);
        RolColSum.Sums sums3 = new RolColSum.Sums(42, 36);
        RolColSum.Sums sums4 = new RolColSum.Sums(58, 40);
        RolColSum.Sums[] expected = RolColSum.asyncSum(matrix);
        assertThat(expected[0]).isEqualTo(sums1);
        assertThat(expected[1]).isEqualTo(sums2);
        assertThat(expected[2]).isEqualTo(sums3);
        assertThat(expected[3]).isEqualTo(sums4);
    }
}