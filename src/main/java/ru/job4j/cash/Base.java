package ru.job4j.cash;

import java.util.Objects;

/**
 * Неблокирующий кеш
 *
 * Base - модель данных
 *
 * @author Ilya Kaltygin
 */
public class Base {
    /* ID уникальный идентификатор */
    private int id;
    /* Version определяет достоверность версии в кеше */
    private int version;
    /* Поле бизнес модели */
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id && version == base.version && Objects.equals(name, base.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, name);
    }
}
