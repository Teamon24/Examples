package core.concurrency.thread.volatileVsSync.state;

public class StateImpl extends State {
    private int i = 0;

    @Override
    public int getI() {
        return this.i;
    }

    @Override
    public void increment() { this.i++; }
}
