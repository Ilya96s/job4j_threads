package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Режим ожидания
 *
 * @author Ilya Kaltygin
 */
public class Wget implements Runnable {

    private final String url;
    private final int speed;
    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    /**
     * Метод считывает данные из сети и если скорость считывания меньше заданной скорости, то выставляется пауза
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long finish = System.currentTimeMillis();
                if ((finish - start) < speed) {
                    try {
                        Thread.sleep(speed - (finish - start));
                    } catch (InterruptedException e) {
                        LOG.error("InterruptedException", e);
                    }
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }

    /**
     * Валидация входных параметров
     * @param args Массив параметров
     */
    private static void validate(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid input parameters. You need write 2 parameters: URL, SPEED");
        }
    }

    public static void main(String[] args) {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
    }
}
