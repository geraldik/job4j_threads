package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(),
                new User(user.getId(), user.getAmount())) == null;
    }

    public boolean update(User user) {
        boolean rsl = false;
        if (users.get(user.getId()) != null) {
            users.put(user.getId(), new User(user.getId(), user.getAmount()));
            rsl = true;
        }
        return rsl;
    }

    public boolean delete(User user) {
        return users.remove(user.getId(), new User(user.getId(), user.getAmount()));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        var firstUser = getUser(fromId);
        var secondUser = getUser(toId);
        if (firstUser.getAmount() - amount >= 0) {
            firstUser.setAmount(firstUser.getAmount() - amount);
            secondUser.setAmount(secondUser.getAmount() + amount);
            update(firstUser);
            update(secondUser);
            rsl = true;
        }
        return rsl;
    }

    public User getUser(int id) {
        User rsl = null;
        if (users.get(id) != null) {
            rsl = new User(users.get(id).getId(), users.get(id).getAmount());
        }
        return rsl;
    }
}
