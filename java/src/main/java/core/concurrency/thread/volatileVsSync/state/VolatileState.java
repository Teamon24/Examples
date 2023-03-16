package core.concurrency.thread.volatileVsSync.state;

public class VolatileState extends State {
    private volatile int i = 0;

    @Override
    public int getI() {
        return this.i;
    }

    @Override
    public void increment() {
        this.i++;
    }
}
