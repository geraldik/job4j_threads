package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;


public class CacheTest {


    @Test
    public void whenAddThanTrue() throws InterruptedException {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        assertTrue(cache.add(model));
    }
    @Test
    public void whenAddTwiceThanFalse() throws InterruptedException {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertFalse(cache.add(model));
    }

    @Test
    public void whenUpdateThanTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertTrue(cache.update(model));
        assertThat(cache.get(model.getId()).getVersion(), is(1));
    }

    @Test (expected = OptimisticException.class)
    public void whenUpdateThanFalse() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.update(new Base(1, 1));
    }

    @Test
    public void whenDeleteThanTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertTrue(cache.delete(model));
    }
    @Test
    public void whenDeleteMissedModelThanFalse() {
        Cache cache = new Cache();
        assertFalse(cache.delete(new Base(1, 0)));
    }
}