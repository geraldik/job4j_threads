package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, base) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base updatedModel = new Base(id, base.getVersion() + 1);
            updatedModel.setName(model.getName());
            return updatedModel;
        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }

    public Base get(Integer key) {
        return memory.get(key);
    }
}