package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Режим ожидания
 *
 * @author Ilya Kaltygin
 */
public class Wget {

    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        try {
                            Thread.sleep(1000);
                            LOG.info("\rLoading : {}% ", i);
                        } catch (InterruptedException e) {
                            LOG.error("InterruptedException", e);
                        }
                    }
                }
        );
        thread.start();
    }
}
