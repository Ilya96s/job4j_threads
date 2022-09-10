package ru.job4j.casoperation;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS - операции
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    /**
     * Методу величивает значение count на единицу
     */
    public void increment() {
        int currentCount;
        int newCount;
        do {
            currentCount = count.get();
            newCount = currentCount + 1;
        } while (!count.compareAndSet(currentCount, newCount));
    }

    /**
     * Метод получает текущее значение count
     */
    public int get() {
        return count.get();
    }
}
