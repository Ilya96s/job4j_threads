package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Реализовать шаблон Producer Consumer
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется, до тех пор пока Consumer не извлечет очередные данные,
 * т.е. в очереди появится свободное место. И наоборот если очередь пуста поток Consumer блокируется, до тех пор пока Producer не поместит в очередь данные
 * @param <T> Обобщенный тип данных
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * Метод помещает объект в коллекцию, если коллекция заполнена нужно перевести нить в состояние ожидания
     * @param value Добавляемый объект
     */
    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= limit) {
                wait();
            }
            queue.offer(value);
            notify();
        }
    }

    /**
     * Метод возвращает объект из внутренней коллекци, если в коллекции нет объектов, то нить переводится в состояние ожидания
     */
    public T pool() throws InterruptedException {
        synchronized (this) {
            while (isEmpty()) {
                wait();
            }
            notify();
            return queue.poll();
        }
    }

    /**
     * Проверяет содержит ли коллекция элементы
     * @return true если коллекция пустая, иначе false
     */
    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }
}
