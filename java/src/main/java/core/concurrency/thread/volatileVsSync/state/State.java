package core.concurrency.thread.volatileVsSync.state;

public abstract class State {
    public abstract void increment();
    abstract public int getI();
}
