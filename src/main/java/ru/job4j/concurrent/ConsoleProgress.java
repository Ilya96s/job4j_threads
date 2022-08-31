package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Прерывание нити
 * Знакомство с методами interrupt() и isInterrupted()
 *
 * @author Ilya Kaltygin
 */
public class ConsoleProgress implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleProgress.class.getName());

    @Override
    public void run() {
        String[] s = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            for (String value : s) {
                try {
                    Thread.sleep(500);
                    LOG.info("\r Load: {} ", value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}