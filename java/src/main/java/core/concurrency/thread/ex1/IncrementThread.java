package core.concurrency.thread.ex1;

public class IncrementThread extends Thread {

    private final Counter checker;
    private final core.concurrency.thread.ex1.state.State state;

    public IncrementThread(final Counter checker,
                           final core.concurrency.thread.ex1.state.State state
    )
    {
        this.checker = checker;
        this.state = state;
    }

    @Override
    public void run() {
        while (!this.checker.stop()) {
            this.state.increment();
        }
    }
}
