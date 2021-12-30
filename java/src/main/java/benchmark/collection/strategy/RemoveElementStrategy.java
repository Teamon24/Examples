package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RemoveElementStrategy<E> extends MethodStrategy<E> {

    private BiConsumer<Collection<E>, E> removeElement;
    private Supplier<E> elementSupplier;

    public RemoveElementStrategy(Class collectionClass,
                                 Supplier<Collection<E>> collectionSupplier,
                                 BiConsumer<Collection<E>, E> removeElement,
                                 Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, null, elementSupplier);
        this.removeElement = removeElement;
        this.elementSupplier = elementSupplier;
    }

    @Override
    public void method(final Collection<E> collection, final Integer index, final E element) {
        throwNoImplementationShouldBe(new Pair[]{Pair.of("index", "present"), Pair.of("element", "present")});
    }

    @Override
    public void method(final Collection<E> collection, final Integer index) {
        throwNoImplementationShouldBe(new Pair[]{Pair.of("index", "present"), Pair.of("element", "absent")});
    }

    @Override
    public void method(final Collection<E> collection, final E element) {
        removeElement.accept(collection, element);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.REMOVE;
    }
}
