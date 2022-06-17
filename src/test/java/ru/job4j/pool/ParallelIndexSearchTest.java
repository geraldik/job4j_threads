package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParallelIndexSearchTest {

    private List<Integer> list;


    @Before
    public void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
    }

    @Test
    public void whenThousandElementThanGetIndex() {
        int index = ParallelIndexSearch.search(list, 0, 999, 900);
        assertThat(index, is(900));
    }

    @Test
    public void whenThousandElementThanGetMinusOne() {
        int index = ParallelIndexSearch.search(list, 0, 999, 1000);
        assertThat(index, is(-1));
    }

}