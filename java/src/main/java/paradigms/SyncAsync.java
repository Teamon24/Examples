package paradigms;

import utils.ConcurrencyUtils;
import utils.RunnableUtils;

/**
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Synchronous/Asynchronous HAS NOTHING TO DO WITH MULTI-THREADING.</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 *
 * <p>Synchronous or Synchronized means "connected", or "dependent" in some way. In org.home.other words, two synchronous tasks must be aware of one another, and one task must execute in some way that is dependent on the org.home.other, such as wait to start until the org.home.other task has completed.
 * <p>Asynchronous means they are totally independent and neither one must consider the org.home.other in any way, either in the initiation or in execution.
 * <ul>
 * <li>Start and end points of tasks A, B, C represented by <, > characters.</li>
 * <li>CPU time slices represented by vertical bars | </li>
 * </ul>
 * <ul>
 * <p><strong>Synchronous (one thread):</p>
 * <pre>{@code
 * thread ->   |<---A---->||<----B---------->||<------C----->|
 * }</pre>
 * </ul>
 *
 * <ul>
 * <p><strong>Synchronous (multi-threaded):
 * <pre>{@code
 * thread A ----> |<---A--->|
 *                          \
 * thread B -------------->  ->|<----B----->|
 *                                           \
 * thread C ------------------------------>   ->|<----C---->|
 * }</pre>
 * </ul>
 *
 * <ul>
 * <p><strong>Asynchronous (one thread):</strong></p>
 * <pre>{@code
 *             A-Start -------------------------------------------- A-End
 *               |    B-Start ----------------------------------------|--- B-End
 *               |      |     C-Start ------------------- C-End       |      |
 *               |      |       |                           |         |      |
 *               V      V       V                           V         V      V
 *    thread--->|<--A--|<--B---|<-C-|-A-|-C-|--A--|-B-|--C-->|---A---->|--B-->|
 *    }</pre>
 * </ul>
 *
 * <ul>
 * <p><strong>Asynchronous (multi-threaded):</strong></p>
 * <pre>{@code
 * thread A --->  |<---A---->|
 * thread B ------->  |<----B---------->|
 * thread C ----------->  |<------C--------->|
 * }</pre>
 * </ul>
 * Technically, the concept of <strong>synchronous/asynchronous really does not have anything to do with threads</strong>. Although, in general, it is unusual to find asynchronous tasks running on the same thread, it is possible, (see below for examples) and it is common to find two or more tasks executing synchronously on separate threads... No, the concept of synchronous/asynchronous has to do solely with whether or not a second or subsequent task can be initiated before the org.home.other (first) task has completed, or whether it must wait. That is all. What thread (or threads), or processes, or CPUs, or indeed, what hardware, the task[s] are executed on is not relevant. Indeed, to make this point I have edited the graphics to show this.
 * <p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>ASYNCHRONOUS EXAMPLE:</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 *
 * <p>In solving many engineering problems, the software is designed to split up the overall problem into multiple individual tasks and then execute them asynchronously. <p>Inverting a matrix, or a finite element analysis problem, are good examples. In computing, sorting a list is an example. The quicksort routine, for example, splits the list into two lists and performs a quicksort on each of them, calling itself (quicksort) recursively. In both of the above examples, the two tasks can (and often were) executed asynchronously. They do not need to be on separate threads. Even a machine with one CPU and only one thread of execution can be coded to initiate processing of a second task before the first one has completed. The only criterion is that the results of one task are not necessary as inputs to the org.home.other task. As long as the start and end times of the tasks overlap, (possible only if the output of neither is needed as inputs to the org.home.other), they are being executed asynchronously, no matter how many threads are in use.
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>SYNCHRONOUS EXAMPLE:</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 *
 * <p>Any process consisting of multiple tasks where the tasks must be executed in sequence, but one must be executed on another machine (Fetch and/or update data, get a stock quote from financial service, etc.). If it's on a separate machine it is on a separate thread, whether synchronous or asynchronous.
 */
public class SyncAsync {
    private static int idCounter = 0;

    public static void main(String[] args) {
        syncTasksByOneThread();
        syncTasksByManyThread();

        asyncTasksByOneThread();
        asyncTasksByManyThread();
    }

    private static void syncTasksByOneThread() {
        ConcurrencyUtils.threadPrintflnTitle("syncTasksByOneThread");

        Thread thread = new Thread(() -> {
            ConcurrencyUtils.threadPrintln("executing sync tasks");
            task(1).run();
            task(2).run();
            task(3).run();
            ConcurrencyUtils.threadPrintln("sync tasks are done");
        });
        thread.setName("thread#" + ++idCounter);
        thread.start();
        ConcurrencyUtils.join(thread);
    }

    private static void syncTasksByManyThread() {
        ConcurrencyUtils.threadPrintflnTitle("syncTasksByManyThread");
        Thread thread1 = thread(task(1));
        Thread thread2 = thread(task(2), thread1);
        Thread thread3 = thread(task(3), thread2);
        ConcurrencyUtils.join(thread3);
    }

    private static void asyncTasksByOneThread() {
        ConcurrencyUtils.threadPrintflnTitle("asyncTasksByOneThread");

        ConcurrencyUtils.join(thread(() -> {
                ConcurrencyUtils.threadPrintln("awaiting all tasks");
                RunnableUtils.waitAndTerminate(task(1), task(2), task(3));
                ConcurrencyUtils.threadPrintln("tasks are done");
            }
        ));
    }

    private static void asyncTasksByManyThread() {
        ConcurrencyUtils.threadPrintflnTitle("asyncTasksByManyThread");
        ConcurrencyUtils.join(
            thread(task(1)),
            thread(task(2)),
            thread(task(3))
        );
    }

    private static Runnable task(int x) {
        return () -> {
            ConcurrencyUtils.threadPrintfln("executing task#%s", x);
            ConcurrencyUtils.sleep(2);
            ConcurrencyUtils.threadPrintfln("task#%s is done", x);
        };
    }

    private static Thread thread(Runnable task) {
        return thread(task, null);
    }

    private static Thread thread(Runnable task, Thread thread) {
        Thread nextThread = new Thread(() -> {
            try {
                if (thread != null) {
                    ConcurrencyUtils.threadPrintfln("waiting for %s", thread.getName());
                    thread.join();
                }
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        nextThread.setName(String.format("thread#%s", ++idCounter));
        nextThread.start();
        return nextThread;
    }
}
