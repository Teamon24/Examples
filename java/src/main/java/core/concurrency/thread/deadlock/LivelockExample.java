package core.concurrency.thread.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;

/**
 * <p>Livelock is another concurrency problem and is similar to deadlock. In livelock, two or more threads keep on transferring states between one another instead of waiting infinitely as we saw in the deadlock example. Consequently, the threads are not able to perform their respective tasks.
 *
 * <p>A great example of livelock is a messaging system where, when an exception occurs, the message consumer rolls back the transaction and puts the message back to the head of the queue. Then the same message is repeatedly read from the queue, only to cause another exception and be put back on the queue. The consumer will never pick up any other message from the queue.
 *
 * <p>To avoid a livelock, we need to look into the condition that is causing the livelock and then come up with a solution accordingly.
 *
 * <p>For example, if we have two threads that are repeatedly acquiring and releasing locks, resulting in livelock, we can design the code so that the threads retry acquiring the locks at random intervals. This will give the threads a fair chance to acquire the locks they need.
 *
 * <p>Another way to take care of the liveness problem in the messaging system example we've discussed earlier is to put failed messages in a separate queue for further processing instead of putting them back in the same queue again.
 */
public class LivelockExample {

    private static final Lock lock1 = new ReentrantLock(true);
    private static final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(LivelockExample::operation1, "T1").start();
        new Thread(LivelockExample::operation2, "T2").start();
    }

    public static void operation(
        Lock lockFirst,
        Lock lockSecond,
        String lockFirstName,
        String lockSecondName,
        String opName
    ) {
        while (true) {
            tryLock(lockFirst, 50);
            threadPrintln(lockFirstName + " acquired, trying to acquire " + lockSecondName + ".");
            sleep(500L);

            if (tryLock(lockSecond)) {
                threadPrintln(lockSecondName + " acquired.");
            } else {
                threadPrintln("cannot acquire " + lockSecondName + ", releasing " + lockFirstName + ".");
                lockFirst.unlock();
                continue;
            }

            threadPrintln("executing " + opName + " operation.");
            break;
        }
        lockSecond.unlock();
        lockFirst.unlock();
    }

    public static void operation2() {
        operation (lock2, lock1, "lock2", "lock1", "second");
    }

    public static void operation1() {
        operation (lock1, lock2, "lock1", "lock2", "first");
    }

    private static boolean tryLock(Lock lock, int i) {
        try {
            return lock.tryLock(i, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean tryLock(Lock lock) {
        return lock.tryLock();
    }
}