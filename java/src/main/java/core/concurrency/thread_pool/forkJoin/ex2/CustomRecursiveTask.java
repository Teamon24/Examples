package core.concurrency.thread_pool.forkJoin.ex2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 20;
    private int[] arr;

    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                .stream()
                .mapToInt(ForkJoinTask::join)
                .sum();
        } else {
            return processing(arr);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        int arrLength = arr.length;
        int middle = arrLength / 2;
        dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, middle)));
        dividedTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, middle, arrLength)));
        return dividedTasks;
    }

    private Integer processing(int[] arr) {
        return Arrays.stream(arr)
            .filter(a -> a > 10 && a < 27)
            .map(a -> a * 10)
            .sum();
    }
}