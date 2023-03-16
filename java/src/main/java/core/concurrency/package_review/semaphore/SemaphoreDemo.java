package core.concurrency.package_review.semaphore;

import core.concurrency.package_review.ExampleUtils;
import core.concurrency.package_review.Task;
import utils.ListGenerator;
import utils.ConcurrencyUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.function.BiFunction;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.interrupt;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;
import static utils.RunnableUtils.submitAll;

public class SemaphoreDemo {
    public static void main(String[] args) {
        int threadsSize = 4;
        int permissionsSize = threadsSize - 1;
        int tasksSize = threadsSize + 3;

        ExampleUtils.title("Semaphore: permissions - " + permissionsSize);
        demo(threadsSize, permissionsSize, tasksSize, SemaphoreDemo::tryAcquireTask);

        permissionsSize = 2;
        ExampleUtils.title("Semaphore: permissions - " + permissionsSize);
        demo(threadsSize, permissionsSize, tasksSize, SemaphoreDemo::acquireTask);

        permissionsSize = 1;
        ExampleUtils.title("Mutex: permissions - " + permissionsSize);
        demo(threadsSize, permissionsSize, tasksSize, SemaphoreDemo::acquireTask);
    }

    public static void demo(int threadsSize,
                            int max,
                            int tasksSize,
                            BiFunction<Semaphore, Integer, Task<Semaphore>> action)
    {
        int jobImitationSeconds = 3;

        Semaphore semaphore = new Semaphore(max, true);
        ExecutorService executorService = fixedThreadPool(threadsSize);

        List<Task<Semaphore>> tasks =
            ListGenerator.create(tasksSize, () -> action.apply(semaphore, jobImitationSeconds));

        RunnableUtils.submitAll(executorService, tasks);
        threadPrintln("awaiting for jobs are done".toUpperCase());
        RunnableUtils.waitAll(submitAll(executorService, tasks));
        threadPrintln("jobs are done".toUpperCase());
        ConcurrencyUtils.terminate(executorService);
    }

    private static Task<Semaphore> tryAcquireTask(Semaphore semaphore, int jobImitationSeconds) {
        return new Task<>(semaphore, it -> {
            if (it.tryAcquire()) {
                threadPrintln("permission was GRANTED; available permissions: " + it.availablePermits());
                sleep(jobImitationSeconds);
                it.release();
                threadPrintln("semaphore was RELEASED");
            } else {
                threadPrintln("permission was DENIED, going further");
                sleep(jobImitationSeconds);
            }
        });
    }

    private static Task<Semaphore> acquireTask(Semaphore semaphore, int jobImitationSeconds) {
        return new Task<>(semaphore, it -> {
            try {
                it.acquire();
                threadPrintln("permission was GRANTED; available: " + it.availablePermits());
                sleep(jobImitationSeconds);
                it.release();
                threadPrintln("semaphore was RELEASED");
            } catch (InterruptedException e) {
                interrupt();
                e.printStackTrace();
            }
        });
    }

}
