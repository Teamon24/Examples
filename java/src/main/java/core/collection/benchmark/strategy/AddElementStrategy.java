package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.strategy.abstrct.ElementStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AddElementStrategy<E> extends ElementStrategy<E> {

    private BiConsumer<Collection<E>, E> addElement;
    private Supplier<E> elementSupplier;

    public AddElementStrategy(final Class<?> collectionClass,
                              Supplier<Collection<E>> collectionSupplier,
                              BiConsumer<Collection<E>, E> addElement,
                              Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, elementSupplier);
        this.addElement = addElement;
        this.elementSupplier = elementSupplier;
    }


    @Override
    public void method(final Collection<E> collection, final E element) {
        this.addElement.accept(collection, element);
    }

    @Override
    public MethodResult createResult(final long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), null, elementSupplier, executionTime);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.ADD_ELEMENT;
    }
}
