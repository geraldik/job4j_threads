package ru.job4j.synch;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;


public class SimpleBlockingQueueTest {

            @Test
            public void whenProducerAndConsumerInteract() throws InterruptedException {
                SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
                int[] expect = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                int[] actual = new int[10];
                Thread producer = new Thread(() -> {
                    for (int i : expect) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Thread consumer = new Thread(() -> {
                    for (int i = 0; i < actual.length; i++) {
                        try {
                            actual[i] = queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                producer.start();
                consumer.start();
                producer.join();
                consumer.join();
                assertArrayEquals(expect, actual);
            }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
