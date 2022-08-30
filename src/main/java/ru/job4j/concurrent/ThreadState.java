package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Состояние нити
 *
 * Нить main должна дождаться завершения нитей first и second и вывести на консоль сообщение, что работа завершена
 *
 * @author Ilya Kaltygin
 */
public class ThreadState {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadState.class.getName());

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    LOG.info(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    LOG.info(Thread.currentThread().getName());
                }
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            LOG.info("Состояние потоков: {} - {}, {} - {}", first.getName(), first.getState(), second.getName(), second.getState());
        }
        LOG.info("Работа программы завершена");
    }
}
