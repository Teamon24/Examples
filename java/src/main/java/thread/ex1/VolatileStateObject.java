package thread.ex1;

public class VolatileStateObject extends StateObject {
    private volatile int i = 0;

    @Override
    public int getI() {
        return i;
    }

    @Override
    void increment() {
        i++;
    }
}
