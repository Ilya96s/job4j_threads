package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;
import java.util.Optional;

/**
 * AccountStorage - хранение счетов пользователя
 *
 * @author Ilya Kaltygin
 */
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * Метод добавляет новых пользователей
     * @param account объект типа Account
     * @return        true если добавили, иначе false
     */
    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    /**
     * Метод обновляет данные пользователей
     * @param account объект типа Account
     * @return        true если обновили, иначе false
     */
    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    /**
     * Метод удаляет пользователей
     * @param id id пользователя
     * @return   true если удалил, иначе false
     */
    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    /**
     * Поиск ппользователя по id
     * @param id id пользователя
     * @return   если объект с таким id существует, то вернуть его, иначе вернуть Optional.empty()
     */
    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * Метод осуществляет денежный перевод между двумя счетами
     * @param fromId Счет с которого происходит перевод
     * @param toId   Счет на который необходимо осуществить перевод
     * @param amount Сумма перевода
     * @return       true если перевод выполнен, иначе false;
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAc = getById(fromId);
        Optional<Account> toAc = getById(toId);
        if (fromAc.isEmpty() || toAc.isEmpty() || fromAc.get().amount() < amount) {
            return false;
        }
        return update(new Account(fromId, fromAc.get().amount() - amount))
                && update(new Account(toId, toAc.get().amount() + amount));
    }
}
