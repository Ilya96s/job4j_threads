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
        ParallelIndexOf<Integer> integerParallelIndexOf = new ParallelIndexOf<>(9, array, 0, array.length - 1);
        int rsl = ParallelIndexOf.findIndex(9, array);
        assertThat(rsl).isEqualTo(8);
    }

    @Test
    void whenTypeIsAnString() {
        String[] array = {"One", "Two", "Three", "Four", "Five", "Six", "15", "Nine", "99", "Seven", "222", "0"};
        ParallelIndexOf<String> stringParallelIndexOf = new ParallelIndexOf<>("Five", array, 0, array.length - 1);
        int rsl = ParallelIndexOf.findIndex("15", array);
        assertThat(rsl).isEqualTo(6);
    }

    @Test
    void whenLinearSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        ParallelIndexOf<Integer> integerParallelIndexOf = new ParallelIndexOf<>(2, array, 0, array.length - 1);
        int rsl = ParallelIndexOf.findIndex(2, array);
        assertThat(rsl).isEqualTo(1);
    }

    @Test
    void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        ParallelIndexOf<Integer> integerParallelIndexOf = new ParallelIndexOf<>(20, array, 0, array.length - 1);
        int rsl = ParallelIndexOf.findIndex(25, array);
        assertThat(rsl).isEqualTo(-1);
    }
}