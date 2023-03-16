package core.concurrency.package_review.locks;

import core.concurrency.package_review.locks.colletion.ReentrantLockedStack;
import core.concurrency.package_review.Task;
import utils.RandomUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.interrupt;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.terminate;
import static utils.ConcurrencyUtils.threadPrintln;
import static utils.ListGenerator.create;
import static utils.RunnableUtils.waitAll;

public class ReentrantLockConditionsDemo {
    public static void main(String[] args) {
        int stackCapacity = 3;
        ReentrantLockedStack stack = new ReentrantLockedStack(stackCapacity);
        ExecutorService pusherService = fixedThreadPool(4, "pushers");
        ExecutorService popperService = fixedThreadPool(4, "poppers");

        int tasksSize = stackCapacity * 2;


        List<Future<?>> pushFutures = futures(tasksSize, pushTask(stack), pusherService);
        sleep(5);
        List<Future<?>> popFutures = futures(tasksSize, popTask(stack), popperService);
        sleep(4);

        List<Future<?>> popFutures2 = futures(tasksSize, popTask(stack), popperService);
        sleep(5);
        List<Future<?>> pushFutures2 = futures(tasksSize, pushTask(stack), pusherService);

        threadPrintln("awaiting for jobs are done".toUpperCase());
        RunnableUtils.waitAll(pushFutures, popFutures, popFutures2, pushFutures2);
        threadPrintln("jobs are done".toUpperCase());

        terminate(popperService, pusherService);
    }

    private static List<Future<?>> futures(
        int tasksSize,
        Supplier<Task<ReentrantLockedStack>> stack,
        ExecutorService pusherService
    ) {
        List<Task<ReentrantLockedStack>> pushTasks = create(tasksSize, stack);
        return RunnableUtils.submitAll(pusherService, pushTasks);
    }

    private static Supplier<Task<ReentrantLockedStack>> popTask(ReentrantLockedStack stack) {
        return () -> new Task<>(stack, ReentrantLockConditionsDemo::pop);
    }

    private static Supplier<Task<ReentrantLockedStack>> pushTask(ReentrantLockedStack stack) {
        return () -> new Task<>(stack, ReentrantLockConditionsDemo::push);
    }

    private static boolean push(ReentrantLockedStack stack) {
        try {
            sleep(RandomUtils.random(1, 5));
            return stack.pushTillNotFull(UUID.randomUUID().toString());
        } catch (InterruptedException e) {
            interrupt();
            e.printStackTrace();
            return false;
        }
    }

    private static String pop(ReentrantLockedStack stack) {
        try {
            sleep(RandomUtils.random(1, 5));
            return stack.popTillNotEmpty();
        } catch (InterruptedException e) {
            interrupt();
            e.printStackTrace();
            return null;
        }
    }
}
