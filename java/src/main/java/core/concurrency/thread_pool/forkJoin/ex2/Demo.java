package core.concurrency.thread_pool.forkJoin.ex2;

import core.utils.ElementUtils;

import java.util.concurrent.ForkJoinPool;

public class Demo {
    public static void main(String[] args) {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        CustomRecursiveTask task = new CustomRecursiveTask(ElementUtils.getRandomIntArray(100));
        commonPool.execute(task);
        int result = task.join();
        System.out.println("Result: " + result);
    }
}
