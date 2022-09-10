package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Неблокирующий кеш
 *
 * @author Ilya kaltygin
 */
class CacheTest {

    @Test
    public void whenAdd() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        assertThat(cache.add(base)).isTrue();
    }

    @Test
    public void whenDelete() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(base);
        assertThat(cache.get(1)).isNull();
    }

    @Test
    public void whenUpdate() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.update(base)).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 2));
    }

}