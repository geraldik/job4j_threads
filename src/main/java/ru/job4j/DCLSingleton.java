package ru.job4j;

/**
 * Переменная inst должна быть volatile, так как возможно ситуация, когда один
 * из потоков прочитает не полностью сконструированный объект
 */

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

}