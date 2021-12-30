package thread.ex2;

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
                    if (!(ID > turn)) break;
                    System.out.printf("turn :#%s, current: #%s\n", turn, ID);
                    lock.wait();
                }
                order.next();
                System.out.println("--------------------------");
                System.out.println("#" + ID + " passed through");
                System.out.println("--------------------------");
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}