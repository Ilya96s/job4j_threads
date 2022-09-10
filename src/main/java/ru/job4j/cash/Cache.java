package ru.job4j.cash;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Неблокирующий кеш
 *
 * Cache - кеш
 *
 * @author Ilya kaltygin
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Метод добавляет объект в кеш
     * @param model Объект типа Base
     * @return      true если объект добавлен, иначе false
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Метод обновляет данные в кеше
     * @param model Оббъект типа base
     * @return      true если данные обновились, иначе false
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, version) -> {
            if (version.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.getId(), version.getVersion() + 1);
        }) != null;
    }

    /**
     * Метод улаляет объект из хранил
     * @param model
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * Получение объекта из кеша
     * @param index Индекс объекта
     * @return      Объект типа Base
     */
    public Base get(int index) {
        return memory.get(index);
    }
}
