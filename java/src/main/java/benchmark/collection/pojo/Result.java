package benchmark.collection.pojo;

abstract public class Result {

    private final MethodInfo methodInfo;
    private final Integer index;
    private final Object element;

    public Result(final String collectionClass,
                  final MethodType methodType,
                  final Integer index,
                  final Object element)
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

    public Object getElement() {
        return element;
    }
}
