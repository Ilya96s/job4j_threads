package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 3.1.6. Пулы 3. ForkJoinPool
 *
 * @author Ilya Kaltygin
 */
class ParallelIndexOfTest {

    @Test
    void whenTypeIsAnInteger() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        int rsl = ParallelIndexOf.findIndex(9, array, 0, array.length - 1);
        assertThat(rsl).isEqualTo(8);
    }

    @Test
    void whenTypeIsAnString() {
        String[] array = {"One", "Two", "Three", "Four", "Five", "Six", "15", "Nine", "99", "Seven", "222", "0"};
        int rsl = ParallelIndexOf.findIndex("15", array, 0, array.length - 1);
        assertThat(rsl).isEqualTo(6);
    }

    @Test
    void whenLinearSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        int rsl = ParallelIndexOf.findIndex(2, array, 0, array.length - 1);
        assertThat(rsl).isEqualTo(1);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        int rsl = ParallelIndexOf.findIndex(25, array, 0, array.length - 1);
        assertThat(rsl).isEqualTo(-1);
    }
}