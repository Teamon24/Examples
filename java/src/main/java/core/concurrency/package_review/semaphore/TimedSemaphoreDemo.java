package core.concurrency.package_review.semaphore;

import core.concurrency.package_review.ExampleUtils;
import core.concurrency.package_review.Task;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import utils.ListGenerator;
import utils.ConcurrencyUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.interrupt;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;
import static utils.RunnableUtils.submitAll;

public class TimedSemaphoreDemo {

    public static final int JOB_IMITATION_SECONDS = 2;
    public static final int TIME_PERIOD = 7;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    public static void main(String[] args) {
        int threadsSize = 4;
        int permissionsSize = threadsSize - 1;
        int tasksSize = threadsSize + 3;

        ExampleUtils.title("Semaphore: permissions - " + permissionsSize);
        demo(threadsSize, permissionsSize, tasksSize, TimedSemaphoreDemo::tryAcquireTask);

        int permissionSize = 1;
        ExampleUtils.title("Semaphore as mutex: permissions - " + permissionSize);
        demo(threadsSize, permissionSize, tasksSize, TimedSemaphoreDemo::acquireTask);
    }

    public static void demo(int threadsSize, int permissionsQuantity, int tasksSize, BiFunction<TimedSemaphore, Integer, Task<TimedSemaphore>> action) {

        TimedSemaphore semaphore = new TimedSemaphore(TIME_PERIOD, TIME_UNIT, permissionsQuantity);
        ExecutorService executorService = fixedThreadPool(threadsSize);

        List<Task<TimedSemaphore>> tasks = ListGenerator.create(tasksSize, () -> action.apply(semaphore, JOB_IMITATION_SECONDS));
        RunnableUtils.submitAll(executorService, tasks);
        threadPrintln("awaiting for jobs are done".toUpperCase());
        RunnableUtils.waitAll(submitAll(executorService, tasks));
        threadPrintln("jobs are done".toUpperCase());
        ConcurrencyUtils.terminate(executorService);
    }

    private static Task<TimedSemaphore> tryAcquireTask(TimedSemaphore semaphore, int jobImitationSeconds) {
        return new Task<>(semaphore, it -> {
            if (it.tryAcquire()) {
                threadPrintln("permission was GRANTED;");
                sleep(jobImitationSeconds);
            } else {
                threadPrintln("permission was DENIED, going further");
                sleep(jobImitationSeconds);
            }
        });
    }

    private static Task<TimedSemaphore> acquireTask(TimedSemaphore semaphore, int jobImitationSeconds) {
        return new Task<>(semaphore, it -> {
            try {
                it.acquire();
                threadPrintln("permission was GRANTED");
                sleep(jobImitationSeconds);
            } catch (InterruptedException e) {
                interrupt();
                e.printStackTrace();
            }
        });
    }
}
