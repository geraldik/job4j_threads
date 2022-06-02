package ru.job4j.thread;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    public int size() {
        return values.size();
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing arguments.");
        }
        for (String s : args) {
            String[] temp = s.split("=", 2);
            if (temp.length != 2
                    || !temp[0].startsWith("-")
                    || temp[0].length() < 2
                    || temp[1].isEmpty()) {
                throw new IllegalArgumentException("Wrong format of argument.");
            }
            values.put(temp[0].substring(1), temp[1]);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}