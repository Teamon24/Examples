package core.concurrency.package_review.phaser;

import utils.ConcurrencyUtils;
import utils.RandomUtils;

import java.util.concurrent.Phaser;

class CustomPhaserThread implements Runnable {
    private Phaser phaser;

    CustomPhaserThread(Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {
            ConcurrencyUtils.threadPrintfln("in phase %s", phaser.getPhase());
            ConcurrencyUtils.threadPrintln("job is executing...");
            ConcurrencyUtils.sleep(RandomUtils.random(1, 5));
            phaser.arriveAndAwaitAdvance();
        }
    }
}
