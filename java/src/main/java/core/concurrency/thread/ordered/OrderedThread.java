package core.concurrency.thread.ordered;

import utils.PrintUtils;

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
        PrintUtils.printfln("turn: #%s, current: #%s", turn, ID);
    }

    private void passedMessage(final int ID) {
        System.out.println("--------------------------");
        System.out.println("#" + ID + " passed through");
        System.out.println("--------------------------");
    }
}