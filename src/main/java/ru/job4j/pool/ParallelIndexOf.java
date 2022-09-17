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
public class ParallelIndexOf<T> extends RecursiveTask<Integer> {
    private final T target;
    private final T[] array;
    private final int indexFrom;
    private final int indexTo;

    public ParallelIndexOf(T target, T[] array, int indexFrom, int indexTo) {
        this.target = target;
        this.array = array;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    /**
     * Метод возвращает индекс указанного объекта
     */
    public int returnIndex() {
        int rsl = -1;
        for (int i = indexFrom; i <= indexTo; i++) {
            if (array[i].equals(target)) {
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
        ParallelIndexOf<T> leftSearch = new ParallelIndexOf<>(target, array, indexFrom, mid);
        ParallelIndexOf<T> rightSearch = new ParallelIndexOf<>(target, array, mid + 1, indexTo);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int findIndex(T target, T[] array, int indexFrom, int indexTo) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexOf<>(target, array, indexFrom, indexTo));
    }
}
