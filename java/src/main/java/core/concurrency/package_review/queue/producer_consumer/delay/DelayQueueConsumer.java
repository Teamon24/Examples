package core.concurrency.package_review.queue.producer_consumer.delay;

import lombok.RequiredArgsConstructor;
import utils.ConcurrencyUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static core.concurrency.package_review.queue.DelayQueueDemo.format;

@RequiredArgsConstructor
public class DelayQueueConsumer implements Runnable {

    private final BlockingQueue<DelayMessage> queue;
    private final Integer delaysNumber;

    public final AtomicInteger consumptionCounter = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < delaysNumber; i++) {
            try {
                queue.take();
                long consumptionTime = System.currentTimeMillis();
                consumptionCounter.incrementAndGet();
                ConcurrencyUtils.threadPrintln("consumed at " + format(consumptionTime));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
