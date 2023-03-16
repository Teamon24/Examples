package core.concurrency.thread.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.sleep;
import static java.lang.System.out;

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

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LivelockExample livelock = new LivelockExample();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }

    public void operation1() {
        while (true) {
            tryLock(lock1, 50);
            out.println("lock1 acquired, trying to acquire lock2.");
            sleep(50L);

            if (tryLock(lock2)) {
                out.println("lock2 acquired.");
            } else {
                out.println("cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }

            out.println("executing first operation.");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }

    private boolean tryLock(Lock lock, int i) {
        try {
            return lock.tryLock(i, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean tryLock(Lock lock) {
        return lock.tryLock();
    }

    public void operation2() {
        while (true) {
            tryLock(lock2, 50);
            out.println("lock2 acquired, trying to acquire lock1.");
            sleep(50L);

            if (tryLock(lock1)) {
                out.println("lock1 acquired.");
            } else {
                out.println("cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }

            out.println("executing second operation.");
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }

    // helper methods

}