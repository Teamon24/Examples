package core.concurrency.thread.ordered;

public class Order {
    private int turn = 1;
    public synchronized void next() {
        turn++;
    }

    public int turn() {
        return turn;
    }
}
