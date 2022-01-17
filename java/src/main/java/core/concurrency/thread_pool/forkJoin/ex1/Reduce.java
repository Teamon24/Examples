package core.concurrency.thread_pool.forkJoin.ex1;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class Reduce<T> extends RecursiveTask<T> {

    protected static final String TEMPLATE = "[%s]: reduce('%s') = '%s'";
    @Getter private final List<T> elements;
    @Getter private final BiFunction<T, T, T> reducer;
    private final int threshold;
    private final int forksAmount;
    @Getter private final String name;

    private final AtomicInteger counter = new AtomicInteger(0);

    public Reduce(
        final String name,
        final List<T> elements,
        final BiFunction<T, T, T> reducer,
        int threshold, int forksAmount)
    {
        this.name = name;
        this.elements = elements;
        this.reducer = reducer;
        this.threshold = threshold;
        this.forksAmount = forksAmount;
    }

    @Override
    protected T compute() {
        int listSize = this.elements.size();

        if (listSize == 0) throw new RuntimeException("Elements amount should not be zero");
        if (listSize == 1) {
            T sole = this.elements.get(0);
            printResult(sole);
            return sole;
        }

        if (listSize <= this.threshold) {
            T reduced = this.elements.stream().reduce(this.reducer::apply).get();
            printResult(reduced);
            return reduced;
        }

        List<Reduce<T>> tasks = getSubtasks(listSize);

        return tasks.stream()
            .map(ForkJoinTask::fork)
            .map(ForkJoinTask::join)
            .reduce(this.reducer::apply)
            .get();
    }

    private List<Reduce<T>> getSubtasks(int listSize) {
        int newSize = listSize / this.forksAmount;
        int residue = listSize % this.forksAmount;

        List<Reduce<T>> tasks = getSubtasksWithNewSize(newSize);

        if (residue > 0) {
            Reduce<T> restTask = getLastSubtask(newSize, residue);
            tasks.add(restTask);
        }
        return tasks;
    }

    private List<Reduce<T>> getSubtasksWithNewSize(int newSize) {
        List<Reduce<T>> tasks = new ArrayList<>();
        System.out.println("Task's list: " + elements(this.elements));

        for (int i = 0; i < this.forksAmount; i++) {
            int fromInclusively = newSize * i;
            int toExclusively = newSize * (i + 1);
            Reduce<T> subtask = subtask(this.elements, fromInclusively, toExclusively);
            tasks.add(subtask);
        }

        return tasks;
    }

    private Reduce<T> getLastSubtask(int newSize, int residue) {
        int fromIndex = newSize * this.forksAmount;
        return subtask(this.elements, fromIndex, fromIndex + residue);
    }

    private Reduce<T> subtask(List<T> elements, int fromInclusively, int toExclusively) {
        List<T> newElements = elements.subList(fromInclusively, toExclusively);
        Reduce<T> subtask = new Reduce<>(
            this.name + "/sub-" + counter.incrementAndGet(),
            newElements,
            this.reducer,
            this.threshold,
            this.forksAmount);
        System.out.println("%s: ".formatted(subtask.getName()) + elements(newElements));
        return subtask;
    }

    private void printResult(T reduced) {
        String threadName = Thread.currentThread().getName();
        String elementsString = elements(this.elements);
        System.out.printf((TEMPLATE) + "%n", threadName, elementsString, reduced);
    }

    private String elements(List<T> elements) {
        return StringUtils.joinWith(",", elements);
    }
}
