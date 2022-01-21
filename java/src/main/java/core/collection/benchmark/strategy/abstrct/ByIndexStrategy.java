package core.collection.benchmark.strategy.abstrct;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ByIndexStrategy<E> extends IndexStrategy<E> {

    private BiConsumer<Collection<E>, Integer> method;

    public ByIndexStrategy(final Class<?> collectionClass,
                           final Supplier<Collection<E>> collectionSupplier,
                           final BiConsumer<Collection<E>, Integer> method,
                           final Function<Collection<E>, Integer> indexGetter)
    {
        super(collectionClass, collectionSupplier, indexGetter);
        this.method = method;
    }

    @Override
    public void method(Collection<E> collection, Integer index) {
        this.method.accept(collection, index);
    }
}
