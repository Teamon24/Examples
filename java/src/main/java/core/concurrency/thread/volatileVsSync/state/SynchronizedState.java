package core.concurrency.thread.volatileVsSync.state;

public class SynchronizedState extends State {
    private int i;

    @Override
    public int getI() {
        return this.i;
    }

    @Override
    public synchronized void increment() {
        this.i++;
    }
}
