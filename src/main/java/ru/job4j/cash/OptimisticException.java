package ru.job4j.cash;

/**
 * Неблокирующий кеш
 *
 * @author ilya Kaltygin
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
