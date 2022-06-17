package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final List<T> list;
    private final T element;

    private final int start;

    private final int end;

    public ParallelIndexSearch(List<T> list, int start, int end, T element) {
        this.list = list;
        this.start = start;
        this.end = end;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 10) {
            for (int i = start; i <= end; i++) {
                if (list.get(i).equals(element)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (end + start) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(list, start, mid, element);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(list, mid + 1, end, element);
        leftSearch.fork();
        rightSearch.fork();
        Integer leftIndex = leftSearch.join();
        Integer rightIndex = rightSearch.join();
        return leftIndex == -1 ? rightIndex : leftIndex;
    }

    public static <T> Integer search(List<T> list, int start, int end, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(list, start, end, element));
    }
}
