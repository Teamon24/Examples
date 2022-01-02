package core.concurrency.thread.ex3;

import core.concurrency.thread.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Demo {
    private final static Object lock = new Object();
    private final static Order order = new Order();
    private final static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        int threadsAmount = 10;
        Function<Integer, Thread> create = (id) -> new OrderedThread(id, order, lock);
        threads.addAll(ThreadUtils.createThreads(threadsAmount, create));
        threads.forEach(Thread::start);
    }
}
