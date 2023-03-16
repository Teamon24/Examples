package core.concurrency.package_review.locks.colletion;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static utils.ConcurrencyUtils.threadPrintfln;

public class ReentrantLockedStack {

    private final Stack<String> stack = new Stack<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition elementAppears = this.lock.newCondition();
    private final Condition freeSpaceAppears = this.lock.newCondition();
    private final int capacity;

    public ReentrantLockedStack(int capacity) {
        this.capacity = capacity;
    }

    public boolean pushTillNotFull(String item) throws InterruptedException {
        try {
            this.lock.lock();
            while (stackIsFull()) {
                threadPrintfln("waiting: stack is full (%s/%s)", this.stack.size(), this.capacity);
                this.freeSpaceAppears.await();
            }
            this.stack.push(item);
            threadPrintfln("pushed: %s/%s", this.stack.size(), this.capacity);
            this.elementAppears.signalAll();
            return true;
        } finally {
            this.lock.unlock();
        }
    }

    private boolean stackIsFull() {
        return this.stack.size() == this.capacity;
    }

    public String popTillNotEmpty() throws InterruptedException {
        try {
            this.lock.lock();
            while (this.stack.isEmpty()) {
                threadPrintfln("waiting: stack is empty (%s/%s)", this.stack.size(), this.capacity);
                this.elementAppears.await();
            }
            String pop = this.stack.pop();
            threadPrintfln("popped: %s/%s", this.stack.size(), this.capacity);
            this.freeSpaceAppears.signalAll();
            return pop;
        } finally {
            this.lock.unlock();
        }
    }
}
