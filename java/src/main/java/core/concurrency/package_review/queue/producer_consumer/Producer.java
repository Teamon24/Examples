package core.concurrency.package_review.queue.producer_consumer;

import java.util.concurrent.BlockingQueue;

public abstract class Producer<E> implements Runnable {
    protected final BlockingQueue<E> queue;
    protected final E stopValue;
    protected final int stopValuePerProducer;
    protected final int elementsAmount;


    public Producer(
        BlockingQueue<E> queue,
        int elementsAmount,
        E stopValue,
        int stopValuePerProducer
    ) {
        this.queue = queue;
        this.elementsAmount = elementsAmount;
        this.stopValue = stopValue;
        this.stopValuePerProducer = stopValuePerProducer;
    }

    public void run() {
        try {
            produce();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public abstract void produce() throws InterruptedException;
}