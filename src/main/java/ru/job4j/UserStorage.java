package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
public class UserStorage {

    private final ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        var firstUser = getUser(fromId);
        var secondUser = getUser(toId);
        if (firstUser != null && secondUser != null
                && firstUser.getAmount() - amount >= 0
        ) {
            firstUser.setAmount(firstUser.getAmount() - amount);
            secondUser.setAmount(secondUser.getAmount() + amount);
            rsl = true;
        }
        return rsl;
    }

    public User getUser(int id) {
        return users.get(id);
    }
}
