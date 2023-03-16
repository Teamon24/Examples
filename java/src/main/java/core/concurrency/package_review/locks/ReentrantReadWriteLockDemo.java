package core.concurrency.package_review.locks;

import core.concurrency.package_review.locks.colletion.ReadWriteLockedHashMap;
import core.concurrency.package_review.Task;
import utils.CollectionUtils;
import utils.ListGenerator;
import utils.RunnableUtils;
import utils.ConcurrencyUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static utils.ConcurrencyUtils.fixedThreadPool;

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>ReentrantReadWriteLock</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>ReentrantReadWriteLock class implements the ReadWriteLock interface.
 *
 * <p>Let's see rules for acquiring the ReadLock or WriteLock by a thread:
 * <ul>
 * <li>Read Lock – if no thread acquired the write lock or requested for it then multiple threads can acquire the read lock</li>
 * <li>Write Lock – if no threads are reading or writing then only one thread can acquire the write lock</li>
 * </ul>
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        ReadWriteLockedHashMap<String, String> hashMap = new ReadWriteLockedHashMap<>();

        ExecutorService executorService = fixedThreadPool(4);

        int size = 10;

        List<Runnable> putTasks = ListGenerator.create(size, () -> randomPut(hashMap));
        List<Runnable> getTasks = ListGenerator.create(size, () -> randomGet(hashMap));

        List<? extends Runnable> tasks = CollectionUtils.toList(putTasks, getTasks);
        Collections.shuffle(tasks);

        RunnableUtils.waitAll(executorService, tasks);
        ConcurrencyUtils.terminate(executorService);
    }

    private static Task<ReadWriteLockedHashMap<String, String>>
    randomPut(ReadWriteLockedHashMap<String, String> hashMap) {
        return new Task<>(hashMap, (it) -> it.put(randomString(), randomString()));
    }

    private static Task<ReadWriteLockedHashMap<String, String>>
    randomGet(ReadWriteLockedHashMap<String, String> hashMap) {
        return new Task<>(hashMap, (it) -> it.get(randomString()));
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}


