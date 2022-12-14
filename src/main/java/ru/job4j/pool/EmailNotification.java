package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService рассылка почты
 *
 * EmailNotification - сервис для рассылки почты
 *
 * @author Ilya Kaltygin
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Метод отправляет почту через ExecutorService
     * @param user Объект типа User
     */
    public void emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                String subject = String.format("Notification {%s} to email {%s} ", user.getUsername(), user.getEmail());
                String body = String.format("Add a new event to {%s} ", user.getUsername());
                send(subject, body, user.getEmail());
                System.out.println(Thread.currentThread().getName());
            }
        });
        close();
    }

    /**
     * Метод закрывает пул
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
