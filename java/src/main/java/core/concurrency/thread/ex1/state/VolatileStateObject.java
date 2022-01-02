package core.concurrency.thread.ex1.state;

public class VolatileStateObject extends StateObject {
    private volatile int i = 0;

    @Override
    public int getI() {
        return i;
    }

    @Override
    public void increment() {
        i++;
    }
}
