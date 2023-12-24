package core.concurrency.thread.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;

/**
 * <p><strong>What Is Deadlock?</strong>
 * <p>A deadlock occurs when two or more threads wait forever for a lock or resource held by another of the threads. Consequently, an application may stall or fail as the deadlocked threads cannot progress.
 *
 * <p>The classic dining philosophers problem nicely demonstrates the synchronization issues in a multi-threaded environment and is often used as an example of deadlock.
 * <p><strong>Avoiding Deadlock</strong>
 * <p>Deadlock is a common concurrency problem in Java. Therefore, we should design a Java application to avoid any potential deadlock conditions.
 *
 * <p>To start with, we should avoid the need for acquiring multiple locks for a thread. However, if a thread does need multiple locks, we should make sure that each thread acquires the locks in the same order, to avoid any cyclic dependency in lock acquisition.
 *
 * <p>We can also use timed lock attempts, like the tryLock method in the Lock interface, to make sure that a thread does not block infinitely if it is unable to acquire a lock.
 */
public class DeadlockExample {

    private static final Lock lock1 = new ReentrantLock(true);
    private static final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(DeadlockExample::operation1, "T1").start();
        new Thread(DeadlockExample::operation2, "T2").start();
    }

    public static void operation1() {
        operation(lock1, lock2, "lock1", "lock2", "first");
    }

    public static void operation2() {
        operation(lock2, lock1, "lock2", "lock1", "second");
    }

    public static void operation(
        Lock lockFirst, Lock lockSecond,
        String lockFirstName, String lockSecondName,
        String opName
    ) {
        lockFirst.lock();
        threadPrintln(lockFirstName + " acquired, waiting to acquire " + lockSecondName + ".");
        sleep(50L);

        lockSecond.lock();
        threadPrintln(lockSecondName + " acquired");
        threadPrintln("executing " + opName + " operation.");

        lockSecond.unlock();
        lockFirst.unlock();
    }
}