package _misc.shipsnpiers;

import _misc.shipsnpiers.GotFreightType.FreightType;
import utils.ConcurrencyUtils;
import utils.RandomUtils;

public class ShipsNPiers {
    public static void main(String[] args) {
        Tunnel tunnel = new Tunnel(3);
        int shipsQuantity = 10;
        ShipsGenerator shipsGenerator = new ShipsGenerator(shipsQuantity, tunnel);
        new Thread(shipsGenerator).start();

        Piers piers = new Piers(tunnel);
        piers.accept(shipsQuantity);
        piers.closeAfterLoadout();
    }
}

class ShipsGenerator implements Runnable {
    private int shipsQuantity;
    private final Tunnel tunnel;

    ShipsGenerator(int shipsQuantity, Tunnel tunnel) {
        this.shipsQuantity = shipsQuantity;
        this.tunnel = tunnel;
    }

    @Override
    public void run() {
        ConcurrencyUtils.setThreadName("generator");
        for (int i = 0; i < shipsQuantity; i++) {
            Ship ship = createShip();
            tunnel.accept(ship);
        }
    }

    private Ship createShip() {
        FreightType freightType = RandomUtils.randomFrom(FreightType.values());
        Integer capacity = 100;
        return new Ship(capacity, freightType);
    }
}


