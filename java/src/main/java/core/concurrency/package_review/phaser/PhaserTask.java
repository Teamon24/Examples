package core.concurrency.package_review.phaser;

import utils.RandomUtils;

import java.util.concurrent.Phaser;

import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintln;

class PhaserTask implements Runnable {
    private final Phaser phaser;

    public PhaserTask(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        threadPrintln("in phase " + this.phaser.getPhase());
        jobImitation();
        threadPrintln("arrived");
        this.phaser.arriveAndAwaitAdvance();
        this.phaser.arriveAndDeregister();
        threadPrintln("deregistred");
    }

    private void jobImitation() {
        threadPrintln("job is being executing");
        sleep(RandomUtils.random(1, 3));
    }
}
