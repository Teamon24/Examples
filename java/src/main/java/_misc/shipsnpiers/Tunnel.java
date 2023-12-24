package _misc.shipsnpiers;

import javax.annotation.Nullable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static utils.ConcurrencyUtils.threadPrintfln;

class Tunnel {
    BlockingQueue<Ship> ships;

    public Tunnel(int capacity) {
        super();
        ships = new ArrayBlockingQueue<>(capacity);
    }

    public void accept(Ship ship) {
        try {
            ships.put(ship);
            threadPrintfln("%s was put", ship);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Ship release() {
        try {
            return ships.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
