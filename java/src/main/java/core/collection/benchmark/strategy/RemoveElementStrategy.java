package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.strategy.abstrct.ElementStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RemoveElementStrategy<E> extends ElementStrategy<E> {

    private BiConsumer<Collection<E>, E> removeElement;
    private Supplier<E> elementSupplier;

    public RemoveElementStrategy(Class collectionClass,
                                 Supplier<Collection<E>> collectionSupplier,
                                 BiConsumer<Collection<E>, E> removeElement,
                                 Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, elementSupplier);
        this.removeElement = removeElement;
        this.elementSupplier = elementSupplier;
    }

    @Override
    public void method(final Collection<E> collection, final E element) {
        removeElement.accept(collection, element);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.REMOVE_ELEMENT;
    }
}
