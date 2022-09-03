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
    private final String fileName;
    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());
    private static final int ONE_SECOND = 1000;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    /**
     * Метод считывает данные из сети и если скорость считывания меньше заданной скорости, то выставляется пауза
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downLoadData = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downLoadData += bytesRead;
                if (downLoadData >= speed) {
                    long finish = System.currentTimeMillis();
                    if ((finish - start) < ONE_SECOND) {
                        try {
                            Thread.sleep(ONE_SECOND - (finish - start));
                        } catch (InterruptedException e) {
                            LOG.error("InterruptedException", e);
                        }
                        downLoadData = 0;
                        start = System.currentTimeMillis();
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
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid input parameters. "
                    + "You need write 3 parameters: url, speed(bytes/second), fileName. "
                    + "For example: https://proof.ovh.net/files/10Mb.dat 1048576 exampleFile.xml");
        }
    }

    public static void main(String[] args) {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
    }
}
