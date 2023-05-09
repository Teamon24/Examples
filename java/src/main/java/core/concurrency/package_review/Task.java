package core.concurrency.package_review;

import java.util.function.Consumer;

public class Task<T> implements Runnable {

    private T lock;
    private Consumer<T> lockAction;

    public Task(T lock, Consumer<T> lockAction) {
        this.lock = lock;
        this.lockAction = lockAction;
    }

    @Override
    public void run() {
        try {
            this.lockAction.accept(this.lock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}