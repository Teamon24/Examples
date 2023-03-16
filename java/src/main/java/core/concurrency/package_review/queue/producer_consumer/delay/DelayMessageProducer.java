package core.concurrency.package_review.queue.producer_consumer.delay;

import lombok.AllArgsConstructor;
import utils.ConcurrencyUtils;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import static core.concurrency.package_review.queue.DelayQueueDemo.format;

@AllArgsConstructor
public class DelayMessageProducer implements Runnable {

    private BlockingQueue<DelayMessage> queue;
    private Integer numbersQuantity;
    private Long delayInMillis;

    @Override
    public void run() {
        for (int i = 0; i < this.numbersQuantity; i++) {
            DelayMessage object = randomMessage();
            ConcurrencyUtils.sleep(1);

            ConcurrencyUtils.threadPrintln(
                "produced to consume at " +
                    format(object.getPlanedConsumptionTime()));
            try {
                this.queue.put(object);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    private DelayMessage randomMessage() {
        return new DelayMessage(
            UUID.randomUUID().toString(),
            this.delayInMillis
        );
    }
}
