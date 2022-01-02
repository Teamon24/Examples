package core.concurrency.thread.ex1.state;

public class SynchronizedStateObject extends StateObject {
    private int i;

    @Override
    public int getI() {
        return i;
    }

    public synchronized void increment() {
        i++;
    }
}
