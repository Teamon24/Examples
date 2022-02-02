package core.concurrency.thread.ex3;

import static utils.PrintUtils.printfln;
import static utils.PrintUtils.println;

public class OrderedThread extends Thread {

    public final int id;
    private final Object lock;
    private Order order;

    OrderedThread(int id, final Order order, final Object lock) {
        this.id = id;
        this.lock = lock;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                int ID = this.id;
                while (true) {
                    int turn = order.turn();
                    if (ID == turn) break;
                    waitMessage(ID, turn);
                    lock.wait();
                }
                order.next();
                passedMessage(ID);
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitMessage(final int ID, final int turn) {
        printfln("turn: #%s, current: #%s", turn, ID);
    }

    private void passedMessage(final int ID) {
        println("--------------------------");
        println("#" + ID + " passed through");
        println("--------------------------");
    }
}