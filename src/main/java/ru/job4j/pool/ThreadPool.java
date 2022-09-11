package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализовать ThreadPool
 *
 * @author Ilya Kaltygin
 */
public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> thread = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(50);

    /**
     * Инициализация пула должны быть по кол-ву ядер в системе
     * В каждую нить передается блокирующая очередь tasks. В методе run() необходимо получить задачу из очереди tasks используя метод pool()
     */
    public ThreadPool() {
        for (int i = 0; i <= size; i++) {
            Thread t = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.pool().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            thread.add(t);
            t.start();
        }
    }

    /**
     * Метод добавляет задачи в блокирующую очередь tasksk
     * @param job Объект типа Runnable
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод завершает все запущенные задачи
     */
    public void shutdown() {
        thread.forEach(Thread::interrupt);
    }
}
