package core.collection.benchmark.strategy;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ByIndexStrategy<E> extends MethodStrategy<E> {

    private BiConsumer<Collection<E>, Integer> byIndexMethod;

    public ByIndexStrategy(final Class collectionClass,
                           final Supplier<Collection<E>> collectionSupplier,
                           final BiConsumer<Collection<E>, Integer> byIndexMethod,
                           final Function<Collection<E>, Integer> indexGetter)
    {
        super(collectionClass, collectionSupplier, indexGetter, null);
        this.byIndexMethod = byIndexMethod;
    }

    @Override
    public void method(Collection<E> collection, Integer index) {
        this.byIndexMethod.accept(collection, index);
    }

    @Override
    public void method(final Collection<E> collection, final Integer index, final E element) {
        Pair[] pairs = {Pair.of("element", "present"), Pair.of("index", "present")};
        throwNoImplementationShouldBe(pairs);
    }

    @Override
    public void method(final Collection<E> collection, final E element) {
        Pair[] pairs = {Pair.of("element", "present"), Pair.of("index", "absent")};
        throwNoImplementationShouldBe(pairs);
    }
}
