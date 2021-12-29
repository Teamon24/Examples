package thread.ex1;

public class IncrementThread extends Thread {

    private final Counter checker;
    private final StateObject stateObject;

    public IncrementThread(final Counter checker,
                           final StateObject stateObject)
    {
        this.checker = checker;
        this.stateObject = stateObject;
    }

    @Override
    public void run() {
        while (!checker.stop()) {
            stateObject.increment();
        }
    }
}
