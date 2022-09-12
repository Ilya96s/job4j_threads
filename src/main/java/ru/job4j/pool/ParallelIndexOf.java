package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 *
 * Реализовать параллельный поиск индексав массиве объектов
 *
 * @author Ilya Kaltygin
 */
public class ParallelIndexOf extends RecursiveTask<Integer> {
    private static final int FALSE = -1;
    private final int target;
    private final Integer[] array;
    private final int indexFrom;
    private final int indexTo;

    public ParallelIndexOf(int target, Integer[] array, int indexFrom, int indexTo) {
        this.target = target;
        this.array = array;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    /**
     * Метод возвращает индекс указанного объекта
     */
    public int returnIndex() {
        int rsl = FALSE;
        for (int i = indexFrom; i < indexTo; i++) {
            if (array[i] == target) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    /**
     * Логика задачи
     */
    @Override
    protected Integer compute() {
        if (indexTo - indexFrom <= 10) {
            return returnIndex();
        }
        int mid = (indexTo - indexFrom) / 2;
        ParallelIndexOf leftSearch = new ParallelIndexOf(target, array, 0, mid);
        ParallelIndexOf rightSearch = new ParallelIndexOf(target, array, mid + 1, indexTo);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }
}
