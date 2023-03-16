package core.concurrency.thread.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.sleep;
import static java.lang.System.out;

/**
 * <p>A deadlock occurs when two or more threads wait forever for a lock or resource held by another of the threads. Consequently, an application may stall or fail as the deadlocked threads cannot progress.
 *
 * <p>The classic dining philosophers problem nicely demonstrates the synchronization issues in a multi-threaded environment and is often used as an example of deadlock.
 *
 * <p>Deadlock is a common concurrency problem in Java. Therefore, we should design a Java application to avoid any potential deadlock conditions.
 *
 * <p>To start with, we should avoid the need for acquiring multiple locks for a thread. However, if a thread does need multiple locks, we should make sure that each thread acquires the locks in the same order, to avoid any cyclic dependency in lock acquisition.
 *
 * <p>We can also use timed lock attempts, like the tryLock method in the Lock interface, to make sure that a thread does not block infinitely if it is unable to acquire a lock.
 */
public class DeadlockExample {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();
        new Thread(deadlock::operation1, "T1").start();
        new Thread(deadlock::operation2, "T2").start();
    }

    public void operation1() {
        lock1.lock();
        out.println("lock1 acquired, waiting to acquire lock2.");
        sleep(50L);

        lock2.lock();
        out.println("lock2 acquired");

        out.println("executing first operation.");

        lock2.unlock();
        lock1.unlock();
    }

    public void operation2() {
        lock2.lock();
        out.println("lock2 acquired, waiting to acquire lock1.");
        sleep(50L);

        lock1.lock();
        out.println("lock1 acquired");

        out.println("executing second operation.");

        lock1.unlock();
        lock2.unlock();
    }
}