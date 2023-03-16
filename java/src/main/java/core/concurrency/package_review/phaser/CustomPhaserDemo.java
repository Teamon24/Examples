package core.concurrency.package_review.phaser;

import utils.ConcurrencyUtils;

public class CustomPhaserDemo {
    public static void main(String... args) {
        CustomPhaser customPhaser = new CustomPhaser(1);
        new CustomPhaserThread(customPhaser);
        new CustomPhaserThread(customPhaser);
        new CustomPhaserThread(customPhaser);

        while (!customPhaser.isTerminated()) {
            customPhaser.arriveAndAwaitAdvance();
        }
        ConcurrencyUtils.threadPrintln("phaser is terminated");
    }
}
