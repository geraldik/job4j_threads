package ru.job4j.io;

import java.io.*;

public final class SimpleStorage implements Storage {

    private final File file;

    public SimpleStorage(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (this) {
            try (OutputStream o = new FileOutputStream(file)) {
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
