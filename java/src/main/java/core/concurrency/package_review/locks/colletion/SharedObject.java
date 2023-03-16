package core.concurrency.package_review.locks.colletion;

import lombok.Getter;
import utils.ConcurrencyUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;

public class SharedObject {

    private final ReentrantLock lock = new ReentrantLock(true);

    @Getter
    private int counter = 0;

    public void increment() {
        this.lock.lock();
        ConcurrencyUtils.threadPrintln("LOCKED");
        try {
            sleep(1);
            this.counter++;
            threadPrintln("incremented.");
        } finally {
            this.lock.unlock();
            ConcurrencyUtils.threadPrintln("UNLOCKED");
        }
    }

    public void tryIncrement() {
        boolean lockIsAcquired;
        try {
            lockIsAcquired = this.lock.tryLock(1, TimeUnit.SECONDS);
            if(lockIsAcquired) {
                ConcurrencyUtils.threadPrintln("locked");
                try {
                    sleep(2);
                    this.counter++;
                    threadPrintln("incremented");
                } finally {
                    this.lock.unlock();
                }
            } else {
                ConcurrencyUtils.threadPrintln("missed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}