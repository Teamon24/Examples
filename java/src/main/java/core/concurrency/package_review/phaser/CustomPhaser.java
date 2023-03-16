package core.concurrency.package_review.phaser;

import utils.ConcurrencyUtils;

import java.util.concurrent.Phaser;

class CustomPhaser extends Phaser {
    CustomPhaser(int parties) {
        super(parties);
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        ConcurrencyUtils.threadPrintln("onAdvance method: completed phase is " + phase);

        // Want to ensure that phaser runs for 2 phases i.e. phase 1
        // or the number of registered parties become zero
        if (phase == 1 || registeredParties == 0) {
            ConcurrencyUtils.threadPrintln("phaser will be terminated");
            return true;
        } else {
            ConcurrencyUtils.threadPrintln("phaser will continue ");
            return false;
        }
    }
}
