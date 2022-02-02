package core.concurrency.thread.ex1.state;

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
