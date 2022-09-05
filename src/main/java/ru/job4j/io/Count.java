package ru.job4j.io;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * JCIP. Настройка библиотеки
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
public final class Count {

    @GuardedBy("this")
    private int count;

    public synchronized void  increment() {
        count++;
    }

    public synchronized int get() {
        return count;
    }
}
