package thread.ex1;

public class StateObjectImpl extends StateObject {
    private int i = 0;

    @Override
    public int getI() {
        return i;
    }

    @Override
    public void increment() { i++; }
}
