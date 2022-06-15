package ru.job4j.pool;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ThreadPoolTest {

    @Test
    public void whenTenTasksThanCount() {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 10; i++) {
            try {
                threadPool.work(count::getAndIncrement);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        threadPool.shutdown();
        assertThat(count.intValue(), is(10));

    }

}