package ru.job4j;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class CASCountTest {

    @Test
    public void whenTwoThreadUseCASCount() throws InterruptedException {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                count.increment();

            }

        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                count.increment();

            }

        });
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(count.get(), is(2000));

    }
}