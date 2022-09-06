package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Динамический список
 * @param <T> Обобщенный тип
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final List<T> list;

    /**
     * При инициализации объекта создается копия списка, который был передан в конструктор
     * @param list Передаваемый объект List
     */
    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    /**
     * Добавление объекта в список
     * @param value Добавляемый объект
     */
    public synchronized void add(T value) {
        list.add(value);
    }

    /**
     * Получение объекта из спика
     * @param index Индекс объекта в списке
     * @return      Объект из списка по индексу index
     */
    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * Копирование списка
     * @param list Исходных список
     * @return     Новый список на основе переданного
     */
    private List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }

    /**
     * Получаем объект - итератор
     * @return Итератор для копии списка
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
