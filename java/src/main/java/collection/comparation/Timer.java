package collection.comparation;

import benchmark.collection.utils.TimeMeasureStrategy;

import java.util.function.Consumer;

/**
 * 06.01.2017.
 * _____________________________________________________________________________________________________________________
 */
public class Timer {

    private Long start;
    private Long finish;

    TimeMeasureStrategy timeMeasureStrategy;

    public Timer(final TimeMeasureStrategy timeMeasureStrategy) {
        this.timeMeasureStrategy = timeMeasureStrategy;
    }

    public long count(Consumer<?> method) {
        start();
        method.accept(null);
        long finish = finish();
        return finish;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public long finish() {
        this.finish = System.currentTimeMillis();
        return result();
    }

    public void discard(){
        this.start  = null;
        this.finish = null;
    }

    public long millisTime() {
        return this.finish - this.start;
    }

    public long result(){
        return this.finish - this.start;
    }
}
