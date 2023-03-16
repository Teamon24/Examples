package core.concurrency.package_review.phaser;

import utils.ConcurrencyUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.threadPrintflnTitle;
import static utils.ConcurrencyUtils.threadPrintln;

public class PhaserDemo {

    public static final String newPhaseTemplate = "new phase %s started";
    public static final String phaseCompletedTemplate = "phase %s completed";

    public static void main(String[] args) {
        int parties = 1;
        Phaser phaser = new Phaser(parties);
        newPhase(phaser, parties * 4, "first-threads");
        newPhase(phaser, parties * 3, "second-threads");
        newPhase(phaser, parties * 2, "third-threads");

        phaser.arriveAndDeregister();
        threadPrintln("deregistred");
    }

    private static void newPhase(Phaser phaser, int threadsNumber, String poolName) {
        int phase = phaser.getPhase();
        threadPrintflnTitle(newPhaseTemplate, phase);

        ExecutorService executorService = startThreads(phaser, threadsNumber, poolName);
        threadPrintln("arrived");
        phaser.arriveAndAwaitAdvance();

        threadPrintflnTitle(phaseCompletedTemplate, phase);
        ConcurrencyUtils.terminate(executorService);
    }

    private static ExecutorService startThreads(Phaser phaser, int size, String poolName) {
        ExecutorService executorService = fixedThreadPool(4, poolName);
        for (int i = 0; i < size; i++) {
            executorService.submit(new PhaserTask(phaser));
        }
        return executorService;
    }
}

