package core.collection.benchmark.strategy.abstrct;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class ElementStrategy<E> extends MethodStrategy<E> {
    protected final Supplier<E> elementGetter;
    protected E element;
    
    public ElementStrategy(Class<?> collectionClass,
                           Supplier<Collection<E>> collectionSupplier,
                           Supplier<E> elementGetter) 
    {
        super(collectionClass, collectionSupplier);
        this.elementGetter = elementGetter;
    }

    public long execute(Collection<E> collection) {
        this.element = this.elementGetter.get();
        return super.timer.count((ignored) -> method(collection, this.element));
    }

    public MethodResult createResult(long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), null, this.element, executionTime);
    }

    public abstract void method(Collection<E> collection, E element);

    @Override
    public MethodType getMethodType() {
        return null;
    }
}
