package core.concurrency.thread.volatileVsSync;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    public static final int LIMIT = 10_000_000;

    private AtomicInteger counter = new AtomicInteger();

    public boolean stop() {
        return counter.incrementAndGet() > LIMIT;
    }
}
