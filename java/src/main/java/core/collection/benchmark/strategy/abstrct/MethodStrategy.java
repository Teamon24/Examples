package core.collection.benchmark.strategy.abstrct;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.TimeMeasureStrategy;
import core.utils.Timer;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class MethodStrategy<E> {
    private final Class<?> collectionClass;
    protected final Supplier<Collection<E>> collectionSupplier;

    protected Timer timer;

    public MethodStrategy(final Class<?> collectionClass,
                          final Supplier<Collection<E>> collectionSupplier)
    {
        this.collectionClass = collectionClass;
        this.collectionSupplier = collectionSupplier;
    }

    public abstract long execute(Collection<E> collection);
    public abstract MethodResult createResult(long executionTime);
    public abstract MethodType getMethodType();

    public long executeAndGetTime(TimeMeasureStrategy timeMeasureStrategy) {
        this.createTimerIfNull(timeMeasureStrategy);
        Collection<E> collection = this.collectionSupplier.get();
        return this.execute(collection);
    };

    public String getCollectionType() {
        return this.collectionClass.getSimpleName();
    }

    private void createTimerIfNull(final TimeMeasureStrategy timeMeasureStrategy) {
        if (timer == null) {
            timer = new Timer(timeMeasureStrategy);
        }
    }
}