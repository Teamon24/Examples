package core.concurrency.package_review.locks;

import core.concurrency.package_review.locks.colletion.StampedLockedHashMap;
import core.concurrency.package_review.Task;
import utils.CollectionUtils;
import utils.ListGenerator;
import utils.RunnableUtils;
import utils.ConcurrencyUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static utils.ConcurrencyUtils.fixedThreadPool;

public class StampedLockDemo {
    public static void main(String[] args) {
        StampedLockedHashMap<String, String> hashMap = new StampedLockedHashMap<>();

        ExecutorService executorService = fixedThreadPool(8);

        int size = 3;

        List<Runnable> putTasks = ListGenerator.create(size, () -> randomPut(hashMap));
        List<Runnable> getTasks = ListGenerator.create(size, () -> randomGet(hashMap));
        List<Runnable> optimisticGetTasks = ListGenerator.create(size,
            () -> randomOptimisticGet(hashMap));

        List<Runnable> tasks = CollectionUtils.toList(
            putTasks, getTasks, optimisticGetTasks);

        RunnableUtils.waitAll(executorService, tasks);
        ConcurrencyUtils.terminate(executorService);
    }

    private static Task<StampedLockedHashMap<String, String>>
    randomPut(StampedLockedHashMap<String, String> hashMap) {
        return new Task<>(hashMap, (it) -> it.put(randomString(), randomString()));
    }

    private static Task<StampedLockedHashMap<String, String>>
    randomGet(StampedLockedHashMap<String, String> hashMap) {
        return new Task<>(hashMap, (it) -> it.get(randomString()));
    }

    private static Task<StampedLockedHashMap<String, String>>
    randomOptimisticGet(StampedLockedHashMap<String, String> hashMap) {
        return new Task<>(hashMap, (it) -> it.optimisticGet(randomString()));
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
