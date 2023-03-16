package core.concurrency.package_review.queue.producer_consumer;

import java.util.concurrent.BlockingQueue;

public abstract class Consumer<E> implements Runnable {
    protected BlockingQueue<E> queue;
    protected final E stopValue;
    
    public Consumer(BlockingQueue<E> queue, E stopValue) {
        this.queue = queue;
        this.stopValue = stopValue;
    }

    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract void consume() throws InterruptedException;
}