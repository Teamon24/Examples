package core.concurrency.package_review.queue.producer_consumer;

import utils.ConcurrencyUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NumberProducer extends Producer<Integer> {
    private String indent;

    public NumberProducer(
        BlockingQueue<Integer> numbersQueue,
        int elementsAmount,
        int stopValue,
        int stopValuePerProducer
    ) {
        super(numbersQueue, elementsAmount, stopValue, stopValuePerProducer);
    }

    @Override
    public void produce() throws InterruptedException {
        for (int i = 0; i < super.elementsAmount; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(100);
            super.queue.put(randomInt);
            this.indent = " ".repeat(String.valueOf(super.elementsAmount).length());
            ConcurrencyUtils.threadPrintln(this.indent + "--> " + "\"" + randomInt + "\"");
        }

        for (int j = 0; j < super.stopValuePerProducer; j++) {
            super.queue.put(super.stopValue);
            ConcurrencyUtils.threadPrintln("stop value was produced");
        }
    }
}
