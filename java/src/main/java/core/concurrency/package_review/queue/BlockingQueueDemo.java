package core.concurrency.package_review.queue;

import core.concurrency.package_review.queue.producer_consumer.NumberConsumer;
import core.concurrency.package_review.queue.producer_consumer.NumberProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Unbounded Queue</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>Creating unbounded queues is simple:
 * <pre>{@code BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();}</pre>
 * <p>The Capacity of blockingQueue will be set to Integer.MAX_VALUE. All operations that add an element to the unbounded queue will never block, thus it could grow to a very large size.
 * <p>The most important thing when designing a producer-consumer program using unbounded BlockingQueue is that consumers should be able to consume messages as quickly as producers are adding messages to the queue. Otherwise, the memory could fill up and we would get an OutOfMemory exception.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Bounded Queue</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>The second type of queues is the bounded queue. We can create such queues by passing the capacity as an argument to a constructor:</p>
 * <pre>{@code BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(10);}</pre>
 * Here we have a blockingQueue that has a <strong>capacity equal to 10</strong>. It means that when a producer tries to add an element to an already full queue, depending on a method that was used to add it (offer(), add() or put()), it will block until space for inserting object becomes available. Otherwise, the operations will fail.
 * <p>Using bounded queue is a good way to design concurrent programs because when we insert an element to an already full queue, that operations need to wait until consumers catch up and make some space available in the queue. It gives us throttling without any effort on our part.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>BlockingQueue API</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>There are two types of methods in the BlockingQueue interface – methods responsible for adding elements to a queue and methods that retrieve those elements. Each method from those two groups <strong>behaves differently in case the queue is full/empty</strong>.
 *
 * <ul><li>Adding Elements
 * <ul>
 * <li><strong>add()</strong> – returns true if insertion was successful, otherwise throws an IllegalStateException</li>
 * <li><strong>put()</strong> – inserts the specified element into a queue, waiting for a free slot if necessary</li>
 * <li><strong>offer()</strong> – returns true if insertion was successful, otherwise false</li>
 * <li><strong>offer(E e, long timeout, TimeUnit unit)</strong> – tries to insert element into a queue and waits for an available slot within a specified timeout</li>
 * </ul>
 * </li>
 * </ul>
 * <ul><li>Retrieving Elements
 * <ul>
 * <li><strong>take()</strong> – waits for a head element of a queue and removes it. If the queue is empty, it blocks and waits for an element to become available</li>
 * <li><strong>poll(long timeout, TimeUnit unit)</strong> – retrieves and removes the head of the queue, waiting up to the specified wait time if necessary for an element to become available. Returns null after a timeout</li>
 * </ul>
 * </li>
 * </ul>
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {

        final int bound = 1;

        final int producersAmount = 2;
        final int consumersAmount = 3;

        final int stopValue = Integer.MAX_VALUE;
        final int stopValuesAmount = consumersAmount / producersAmount;
        final int restStopValuesAmount = consumersAmount % producersAmount;

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(bound);

        final int numbersAmount = 5;

        for (int i = 0; i < producersAmount; i++) {
            int stopValuePerProducer = stopValuesAmount;
            String name = producerName(i);

            if (i + 1 == producersAmount) {
                stopValuePerProducer = stopValuesAmount + restStopValuesAmount;
                name = "Last producer";
            }

            start(
                new NumberProducer(queue, numbersAmount, stopValue, stopValuePerProducer),
                name);
        }

        for (int j = 0; j < consumersAmount; j++) {
            start(
                new NumberConsumer(queue, stopValue),
                consumerName(j));
        }
    }

    private static void start(Runnable runnable, String name) {
        Thread consumer = new Thread(runnable);
        consumer.setName(name);
        consumer.start();
    }

    private static String consumerName(int j) {
        return "Consumer-" + j;
    }

    private static String producerName(int i) {
        return "Producer-" + i;
    }

}
