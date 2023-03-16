package core.concurrency.package_review.queue;

import core.concurrency.package_review.queue.producer_consumer.delay.DelayMessage;
import core.concurrency.package_review.queue.producer_consumer.delay.DelayMessageProducer;
import core.concurrency.package_review.queue.producer_consumer.delay.DelayQueueConsumer;
import utils.ConcurrencyUtils;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayQueueDemo {
    private static final SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("mm:ss:SS");

    public static String format(long consumptionTime) {
        return simpleDateFormat.format(consumptionTime);
    }

    public static void main(String[] args) {
        long delayInMillis = 10000;
        int numbersQuantity = 10;

        BlockingQueue<DelayMessage> queue = new DelayQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numbersQuantity);
        DelayMessageProducer producer = new DelayMessageProducer(queue, numbersQuantity, delayInMillis);
        DelayMessageProducer expiredMessagesProducer = new DelayMessageProducer(queue, 3, -1000L);

        executor.submit(producer);
        executor.submit(expiredMessagesProducer);
        executor.submit(consumer);

        ConcurrencyUtils.terminate(delayInMillis * 3, executor);
        executor.shutdown();

        ConcurrencyUtils.threadPrintln(consumer.consumptionCounter.get() == numbersQuantity);
    }
}

