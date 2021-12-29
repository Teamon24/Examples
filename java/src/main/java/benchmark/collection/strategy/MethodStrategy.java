package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;
import benchmark.collection.utils.TimeMeasureStrategy;
import collection.comparation.Timer;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class MethodStrategy<E> {

    private Class<?> collectionClass;
    private Supplier<Collection<E>> collectionSupplier;
    private Timer timer;

    public MethodStrategy(final Class<?> collectionClass,
                          final Supplier<Collection<E>> collectionSupplier)
    {
        this.collectionClass = collectionClass;
        this.collectionSupplier = collectionSupplier;
    }

    public long executeAndGetTime(TimeMeasureStrategy timeMeasureStrategy) {
        createTimerIfNull(timeMeasureStrategy);
        Collection<E> collection = this.collectionSupplier.get();
        return timer.count((ignored) -> method(collection));

    }

    public String getCollectionType() {
        return this.collectionClass.getSimpleName();
    }

    public abstract void method(Collection<E> collection);

    public abstract MethodResult createResult(long executionTime);
    public abstract MethodType getMethodType();

    private void createTimerIfNull(final TimeMeasureStrategy timeMeasureStrategy) {
        if (timer == null) {
            timer = new Timer(timeMeasureStrategy);
        }
    }

}
