package ru.job4j.concurrent;

/**
 * Синхронизация общих ресурсов
 *
 * @author Ilya Kaltygin
 */
public final class Cache {

    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
