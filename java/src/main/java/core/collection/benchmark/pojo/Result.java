package core.collection.benchmark.pojo;

abstract public class Result<T> {

    private final MethodInfo methodInfo;
    private final Integer index;
    private final T element;

    public Result(final String collectionClass,
                  final MethodType methodType,
                  final Integer index,
                  final T element)
    {
        this.methodInfo = MethodInfo.get(collectionClass, methodType);
        this.index = index;
        this.element = element;
    }

    public String getCollectionClass() {
        return this.methodInfo.getCollectionClass();
    }

    public MethodType getMethodType() {
        return this.methodInfo.getMethodType();
    }

    public Integer getIndex() {
        return index;
    }

    public T getElement() {
        return element;
    }
}
