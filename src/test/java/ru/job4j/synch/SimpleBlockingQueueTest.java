package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Ilya Kaltygin
 */
class SimpleBlockingQueueTest {

    @Test
    void whenOfferThenPool() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(15);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 15; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        Thread consumer = new Thread(
                () -> {
                    while (list.size() < 15) {
                        try {
                            Integer i = queue.pool();
                            list.add(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.size()).isEqualTo(15);
    }

    @Test
    void whenFetchAllThenGetIt() throws InterruptedException {
        /* buffer - нужен, чтобы собрать все данные в список и проверить их в конце выполнения теста.*/
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(15);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    /* Зачем нужна двойная проверка.
                    Если производитель закончил свою работу и сразу подаст сигнал об отключении потребителя,
                    то мы не сможем прочитать все данные. С другой стороны, если мы успели прочитать все данные и
                    находимся в режиме wait пришедший сигнал запустит нить и проверит состояние очереди и завершит цикл.
                    Потребитель закончит свою работу.*/
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.pool());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}