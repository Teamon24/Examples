package core.concurrency.package_review.collection;

import com.google.common.collect.Sets;
import lombok.val;
import utils.RandomUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

import static utils.ConcurrencyUtils.join;
import static utils.ConcurrencyUtils.setThreadName;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.startWithDelay;
import static utils.ConcurrencyUtils.threadPrintfln;
import static utils.ConcurrencyUtils.threadPrintln;

/**
 * <table style="width: 100%; border: 1px solid #69bf75; border-collapse: collapse; overflow-wrap: break-word;">
 * <tbody>
 * <tr style="background-color: #69bf75;color: #ffffff">
 * <th>Feature</th>
 * <th>LinkedBlockingQueue</th>
 * <th>ConcurrentLinkedQueue</th>
 * </tr>
 * <tr>
 * <td><strong>Blocking Nature</strong></td>
 * <td>It is a blocking queue and implements the <em>BlockingQueue</em> interface</td>
 * <td>It is a non-blocking queue and does not implement the <em style="font-family: inherit;font-size: inherit">BlockingQueue </em><span style="font-family: inherit;font-size: inherit">interface</span></td>
 * </tr>
 * <tr>
 * <td><strong>Queue Size</strong></td>
 * <td>It is an optionally bounded queue, which means there are provisions to define the queue size during creation</td>
 * <td>It is an unbounded queue, and there is no provision to specify the queue size during creation</td>
 * </tr>
 * <tr>
 * <td><strong>Locking Nature</strong></td>
 * <td>It is<strong>&nbsp;a lock-based queue</strong></td>
 * <td>It is <strong>a lock-free queue</strong></td>
 * </tr>
 * <tr>
 * <td><strong>Algorithm</strong></td>
 * <td>It implements<strong> its locking based on <em>two-lock queue</em> algorithm</strong></td>
 * <td>It relies on the <strong style="font-family: inherit;font-size: inherit">Michael &amp; Scott algorithm for non-blocking, lock-free queues</strong></td>
 * </tr>
 * <tr>
 * <td><strong>Implementation</strong></td>
 * <td>In the <em style="font-family: inherit;font-size: inherit">two-lock queue</em><span style="font-family: inherit;font-size: inherit"> algorithm mechanism, </span><em style="font-family: inherit;font-size: inherit">LinkedBlockingQueue </em><span style="font-family: inherit;font-size: inherit">uses two different locks – the </span><em style="font-family: inherit;font-size: inherit">putLock</em><span style="font-family: inherit;font-size: inherit"> and the </span><em style="font-family: inherit;font-size: inherit">takeLock</em><span style="font-family: inherit;font-size: inherit">. The </span><em style="font-family: inherit;font-size: inherit">put/take</em><span style="font-family: inherit;font-size: inherit"> operations uses the first lock type, and the </span><em style="font-family: inherit;font-size: inherit">take/poll</em><span style="font-family: inherit;font-size: inherit"> operations use the other lock type</span></td>
 * <td><strong>It uses CAS (Compare-And-Swap</strong>) for its operations</td>
 * </tr>
 * <tr>
 * <td><strong>Blocking Behavior</strong></td>
 * <td>It is a blocking queue. So, it blocks the accessing threads when the queue is empty</td>
 * <td>It does not block the accessing thread when the queue is empty and returns <em>null</em></td>
 * </tr>
 * </tbody>
 * </table>
 */
public class Queues {
    private static final Supplier<Integer> random = () -> RandomUtils.random(0, 100);

    private static final long ITERATOR_SLEEP = 250L;
    private static final long ADDER_SLEEP = 100L;

    public static void main(String[] args) {
        HashSet<Integer> initialValues = Sets.newHashSet(1, 2, 3);

        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>(initialValues);
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(initialValues);

        int elementsNumber = 2;
        run("blocking", linkedBlockingQueue, elementsNumber);
        run("concurrent", concurrentLinkedQueue, elementsNumber);

        execute("blocking", "poll", linkedBlockingQueue, Queue::poll);
        execute("concurrent", "poll", concurrentLinkedQueue, Queue::poll);

        execute("blocking", "take", linkedBlockingQueue, Queues::take);

    }

    private static Integer take(BlockingQueue<Integer> queue) {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static
    <Q extends Queue<Integer>>
    void execute(String adderName, String consumer, Q collection, Function<Q, Integer> poll) {
        collection.clear();
        setThreadName(consumer);
        Thread adder = adder(adderName, collection, 1);

        startWithDelay(adder, 1000L);

        threadPrintfln("size = %s", collection.size());
        Integer element = poll.apply(collection);
        threadPrintfln("get: %s", element);

        join(adder);
    }


    private static void run(
        String name,
        Collection<Integer> collection,
        Integer addedElementsNumber
    ) {
        val go = new AtomicBoolean(true);
        val iterator = iterator(go, name, collection);
        val consumer = adder(go, name, collection, addedElementsNumber);

        iterator.start();
        sleep(100L);
        consumer.start();

        join(iterator, consumer);
    }


    private static Thread adder(
        AtomicBoolean go,
        String queue,
        Collection<Integer> collection,
        Integer i
    ) {
        return thread(queue, () -> {
                for (int c = i; c > 0; c--) {
                    Integer integer = random.get();
                    threadPrintln("+ " + integer);
                    collection.add(integer);
                    sleep(ADDER_SLEEP);
                }
                go.set(false);
            }
        );
    }

    private static Thread adder(
        String queue,
        Collection<Integer> collection,
        Integer i
    ) {
        return thread(queue, () -> {
                for (int c = i; c > 0; c--) {
                    Integer integer = random.get();
                    threadPrintln("+ " + integer);
                    collection.add(integer);
                    sleep(ADDER_SLEEP);
                }
            }
        );
    }

    private static Thread iterator(
        AtomicBoolean go,
        String queue,
        Collection<Integer> collection
    ) {

        return thread(queue, () -> {
                int i = Thread.currentThread().getName().length() * 2;
                while (go.get()) {
                    threadPrintln("-".repeat(i));
                    collection.forEach(it -> {
                        threadPrintln(it);
                        sleep(ITERATOR_SLEEP);
                    });
                }
                threadPrintln("-".repeat(i));
                threadPrintln("last iteration: adder finished");
                collection.forEach(it -> {
                    threadPrintln(it);
                    sleep(ITERATOR_SLEEP);
                });

            }
        );
    }

    private static Thread thread(String name, Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(name);
        return thread;
    }
}
