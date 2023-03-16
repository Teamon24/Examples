package utils;

import core.collection.benchmark.utils.TimeMeasureStrategy;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Timer {

    public static final String FORMAT = "%02ds:%02d:%02d:%02d";

    private Long start;
    private Long finish;

    TimeMeasureStrategy timeMeasureStrategy;

    public Timer(final TimeMeasureStrategy timeMeasureStrategy) {
        this.timeMeasureStrategy = timeMeasureStrategy;
    }

    public long count(Consumer<?> method) {
        start();
        method.accept(null);
        return finish();
    }

    public <T> T count(Supplier<T> method) {
        discard();
        start();
        T result = method.get();
        finish();
        return result;
    }

    public void start() {
        this.start = timeMeasureStrategy.measure();
    }

    public long finish() {
        this.finish = timeMeasureStrategy.measure();
        return result();
    }

    public void discard() {
        this.start  = null;
        this.finish = null;
    }

    public long result(){
        return this.finish - this.start;
    }

    public static String format(long time, TimeUnit timeUnit) {
        long seconds = timeUnit.toSeconds(time);
        long millis = timeUnit.toMillis(time) % 1000;
        long micros = timeUnit.toMicros(time) % 1000;
        long nanos = timeUnit.toNanos(time) % 1000;
        return String.format(FORMAT, seconds, millis, micros, nanos);
    }
}