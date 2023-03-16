package core.concurrency.package_review.queue.producer_consumer;

import utils.ConcurrencyUtils;

import java.util.concurrent.BlockingQueue;

public class NumberConsumer extends Consumer<Integer> {

    public NumberConsumer(BlockingQueue<Integer> queue, int stopValue) {
        super(queue, stopValue);
    }

    @Override
    public void consume() throws InterruptedException {
        while (true) {
            ConcurrencyUtils.sleep(5);
            Integer number = super.queue.take();
            if (number.equals(super.stopValue)) {
                ConcurrencyUtils.threadPrintln("stop value was consumed.");
                return;
            }
            ConcurrencyUtils.threadPrintln(number + " <-");
        }
    }
}
