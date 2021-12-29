package thread.ex1;

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
