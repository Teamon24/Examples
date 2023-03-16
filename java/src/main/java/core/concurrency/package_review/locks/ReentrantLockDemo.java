package core.concurrency.package_review.locks;

import core.concurrency.package_review.locks.colletion.SharedObject;
import core.concurrency.package_review.ExampleUtils;
import core.concurrency.package_review.Task;
import utils.ListGenerator;
import utils.RunnableUtils;
import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static utils.ConcurrencyUtils.fixedThreadPool;

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Differences Between Lock and Synchronized Block</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>There are few differences between the use of synchronized block and using Lock API's:
 * <ul>
 * <li><strong>A synchronized block is fully contained within a method</strong>– we can have Lock API's lock() and unlock() operation in separate methods</li>
 * <li>A synchronized block doesn't support the fairness, any thread can acquire the lock once released, no preference can be specified. <strong>We can achieve fairness within the Lock APIs by specifying the fairness property</strong>. It makes sure that longest waiting thread is given access to the lock</li>
 * <li>A thread gets blocked if it can't get an access to the synchronized block. <strong>The Lock API provides tryLock() method. The thread acquires lock only if it's available and not held by any org.home.other thread</strong>. This reduces blocking time of thread waiting for the lock</li>
 * <li>A thread which is in “waiting” state to acquire the access to synchronized block, can't be interrupted. <strong>The Lock API provides a method lockInterruptibly() which can be used to interrupt the thread when it's waiting for the lock</strong></li>
 * </ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Lock API</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>Let's take a look at the methods in the Lock interface:
 * <ul>
 * <li><strong>void lock()</strong> – acquire the lock if it's available; if the lock isn't available a thread gets blocked until the lock is released</li>
 * <li><strong>void lockInterruptibly()</strong> – this is similar to the lock(), but it allows the blocked thread to be interrupted and resume the execution through a thrown java.lang.InterruptedException</li>
 * <li><strong>boolean tryLock()</strong> – this is a non-blocking version of lock() method; it attempts to acquire the lock immediately, return true if locking succeeds</li>
 * <li><strong>boolean tryLock(long timeout, TimeUnit timeUnit)</strong> – this is similar to tryLock(), except it waits up the given timeout before giving up trying to acquire the Lock</li>
 * <li><strong>void unlock()</strong> – unlocks the Lock instance</li>
 * </ul>
 * A locked instance should always be unlocked to avoid deadlock condition. A recommended code block to use the lock should contain a try/catch and finally block:
 * <pre>{@code
 * Lock lock = ...;
 * lock.lock();
 * try {
 *     // access to the shared resource
 * } finally {
 *     lock.unlock();
 * }
 * }</pre>
 * In addition to the Lock interface, we have a ReadWriteLock interface which maintains a pair of locks, one for read-only operations, and one for the write operation. The read lock may be simultaneously held by multiple threads as long as there is no write.
 * </ul>
 * <ul>ReadWriteLock declares methods to acquire read or write locks:<ul>
 * <li>Lock readLock() – returns the lock that's used for reading</li>
 * <li>Lock writeLock() – returns the lock that's used for writing</li>
 * </ul>
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {

        ExecutorService executorService = fixedThreadPool(3);

        ExampleUtils.title("ReenteredLock#lock()");
        runTasks(new SharedObject(), executorService, SharedObject::increment);

        ExampleUtils.title("ReenteredLock#tryLock()");
        runTasks(new SharedObject(), executorService, SharedObject::tryIncrement);

        ConcurrencyUtils.terminate(executorService);

    }

    private static void runTasks(
        SharedObject sharedObject,
        ExecutorService executorService, Consumer<SharedObject> method
    ) {

        int tasksSize = 10;
        List<Future<?>> tryFutures = ListGenerator
            .create(tasksSize, () -> new Task<>(sharedObject, method))
            .stream()
            .map(executorService::submit)
            .collect(Collectors.toList());

        RunnableUtils.waitAll(tryFutures);
        ConcurrencyUtils.threadPrintln("counter: " + sharedObject.getCounter() + "/" + tasksSize);
    }
}
