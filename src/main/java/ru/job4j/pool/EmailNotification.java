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
        String subject = String.format("Notification {%s} to email {%s} ", user.getUsername(), user.getEmail());
        String body = String.format("Add a new evet to {%s} ", user.getUsername());
        pool.submit(new Runnable() {
            @Override
            public void run() {
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
    }

    public void send(String subject, String body, String email) {

    }
}
