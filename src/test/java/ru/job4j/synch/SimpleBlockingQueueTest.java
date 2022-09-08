package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}