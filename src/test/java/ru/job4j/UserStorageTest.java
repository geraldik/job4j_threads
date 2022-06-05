package ru.job4j;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddThanThenTrue() {
        var storage = new UserStorage();
        assertTrue(storage.add(new User(1, 100)));
    }

    @Test
    public void whenAddTwiceThenFalse() {
        var storage = new UserStorage();
        storage.add(new User(1, 100));
        assertFalse(storage.add(new User(1, 100)));
    }

    @Test
    public void whenUpdateThenTrue() {
        var storage = new UserStorage();
        storage.add(new User(1, 100));
        var expect = new User(1, 200);
        assertTrue(storage.update(new User(1, 200)));
        assertEquals(expect, storage.getUser(1));
    }

    @Test
    public void whenTransferThenTrue() {
        var storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 100));
        storage.transfer(1, 2, 50);
        var expect1 = new User(1, 50);
        var expect2 = new User(2, 150);
        assertEquals(expect1, storage.getUser(1));
        assertEquals(expect2, storage.getUser(2));
    }

    @Test
    public void whenAddAndDeleteThanNull() {
        var storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.delete(new User(1, 100));
        assertNull(storage.getUser(1));
    }

    @Test
    public void whenAddAndDeleteWrongThanFalse() {
        var storage = new UserStorage();
        storage.add(new User(1, 100));
        assertFalse(storage.delete(new User(1, 10)));
    }
}