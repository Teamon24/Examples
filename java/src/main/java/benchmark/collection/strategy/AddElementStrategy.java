package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AddElementStrategy<E> extends MethodStrategy<E> {

    private BiConsumer<Collection<E>, E> addElement;
    private E element;

    public AddElementStrategy(final Class<?> collectionClass,
                              Supplier<Collection<E>> collectionSupplier,
                              BiConsumer<Collection<E>, E> addElement,
                              E element)
    {
        super(collectionClass, collectionSupplier);
        this.addElement = addElement;
        this.element = element;
    }

    @Override
    public void method(Collection<E> collection) {
        addElement.accept(collection, element);
    }

    @Override
    public MethodResult createResult(final long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), null, element, executionTime);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.ADD;
    }
}
