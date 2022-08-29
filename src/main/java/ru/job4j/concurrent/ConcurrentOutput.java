package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1. Запуск нити. Thread#start()
 *
 * Создание потоков исполнения
 *
 * @author Ilya Kaltygin
 */
public class ConcurrentOutput {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentOutput.class.getName());

    public static void main(String[] args) {
        Thread another1 = new Thread(
                () -> LOG.info(Thread.currentThread().getName())
        );
        another1.start();

        Thread second = new Thread(
                () -> LOG.info(Thread.currentThread().getName())
        );
        second.start();

    }
}