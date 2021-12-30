package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodType;
import benchmark.collection.utils.TriConsumer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddByIndexStrategy<E> extends MethodStrategy<E> {

    private TriConsumer<Collection<E>, Integer, E> addByIndex;
    private Function<Collection<E>, Integer> indexSupplier;
    private Supplier<E> elementSupplier;

    public AddByIndexStrategy(Class collectionClass,
                              Supplier<Collection<E>> collectionSupplier,
                              TriConsumer<Collection<E>, Integer, E> addByIndex,
                              Function<Collection<E>, Integer> indexSupplier,
                              Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, indexSupplier, elementSupplier);
        this.addByIndex = addByIndex;
        this.indexSupplier = indexSupplier;
        this.elementSupplier = elementSupplier;
    }

    @Override
    public void method(final Collection<E> collection, final Integer index, final E element) {
        this.addByIndex.accept(collection, index, element);
    }

    @Override
    public void method(final Collection<E> collection, final Integer index) {
        throwNoImplementationShouldBe(new Pair[]{Pair.of("index", "present"), Pair.of("element", "absent")});
    }

    @Override
    public void method(final Collection<E> collection, final E element) {
        throwNoImplementationShouldBe(new Pair[]{Pair.of("index", "absent"), Pair.of("element", "present")});
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.ADD;
    }
}
