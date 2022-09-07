package ru.job4j.synch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Управление нитью через wait
 * Блокировка потока по условию счетчика.
 *
 * @author Ilya Kaltygin
 */
public final class CountBarrier {

    private final static Logger LOG = LoggerFactory.getLogger(CountBarrier.class.getName());
    private final Object monitor = this;
    /**
     * Колв-во вызовов метода count()
     */
    private final int total;
    /**
     * Счетчик
     */
    private int count;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод увеличивает значение счетчика и будит все нити
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    /**
     * Метод переводит поток в режим ожидания если count < total
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
