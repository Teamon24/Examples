package aop.ex1;

import core.concurrency.ConcurrencyUtils;

public aspect Synchronization {
    private static final Object lock = new Object();

    pointcut syncJointPoint(): execution(@aop.ex1.Synchronizes * *.*(..)); // or call()

    Object around(): syncJointPoint() {
        synchronized(lock) {
            ConcurrencyUtils.threadPrintln("in lock");
            return proceed();
        }
    }
}
