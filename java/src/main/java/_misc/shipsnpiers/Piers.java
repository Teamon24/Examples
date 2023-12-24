package _misc.shipsnpiers;

import utils.CallableUtils;
import utils.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static _misc.shipsnpiers.GotFreightType.FreightType.CLOTHES;
import static _misc.shipsnpiers.GotFreightType.FreightType.ELECTRONICS;
import static _misc.shipsnpiers.GotFreightType.FreightType.FRUITS;
import static utils.ClassUtils.simpleName;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadPrintfln;

class Piers {

    private final Tunnel tunnel;
    private final List<Pier> piers;
    private List<Future<Ship>> futures;
    private ExecutorService threadPoolExecutor;
    private int counter = 1;

    public Piers(Tunnel tunnel) {
        this.tunnel = tunnel;
        this.piers = createPiers();
        this.threadPoolExecutor = Executors.newFixedThreadPool(
            piers.size(),
            runnable -> new Thread(runnable, String.format("pier #%s", counter++))
        );
    }

    public void closeAfterLoadout() {
        ConcurrencyUtils.getAll(futures);
        ConcurrencyUtils.terminate(threadPoolExecutor);
    }

    public void accept(int shipsQuantity) {
        Collection<Callable<Ship>> callables = new ArrayList<>();
        for (int i = 0; i < shipsQuantity; i++) {
            Callable<Ship> runnable = () -> {
                Ship ship = tunnel.release();
                GotFreightType.FreightType type = ship.getType();
                int loadoutSpeed = getPierBy(type).getLoadoutSpeed();
                loadout(ship, loadoutSpeed);
                return ship;
            };

            callables.add(runnable);
        }

        futures = CallableUtils.invokeAll(threadPoolExecutor, callables);
    }

    private void loadout(Ship ship, int loadoutSpeed) {
        GotFreightType.FreightType type = ship.getType();
        long loadoutTime = ship.getCapacity() * 1000L / loadoutSpeed;
        sleep(loadoutTime);
        threadPrintfln("loadout of \"%s\" has been finished within %s", type.name(), loadoutTime);
    }

    private Pier getPierBy(GotFreightType.FreightType type) {
        Optional<Pier> pier = piers.stream()
            .filter(p -> p.freightType == type)
            .findFirst();

        pier
            .ifPresentOrElse((p) -> {
            }, () -> {
                String message = String.format("There is no %s with %s \"%s\"", Pier.FreightLoadoutSpeed.class.getName(), simpleName(type), type);
                throw new NoSuchElementException(message);
            });

        return pier.get();
    }

    private static ArrayList<Pier> createPiers() {
        ArrayList<Pier> piersList = new ArrayList<>();
        piersList.add(new Pier(ELECTRONICS));
        piersList.add(new Pier(FRUITS));
        piersList.add(new Pier(CLOTHES));
        return piersList;
    }
}
