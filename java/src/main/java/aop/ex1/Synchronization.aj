package aop.ex1;

@interface Synchronizes {}

public aspect Synchronization {
    private static final Object lock = new Object();

    pointcut syncJointPoint(): execution(@aop.ex1.Synchronizes * *.*(..)); // or call()

    Object around(): syncJointPoint() {
        synchronized(lock) {
            return proceed();
        }
    }
}
