package core.collection.benchmark.pojo;

public class MethodResult<T> extends Result<T> {

    private final Long executionTime;

    public MethodResult(final String listClass,
                        final MethodType methodType,
                        final Integer index,
                        final T element,
                        final long executionTime)
    {
        super(listClass, methodType, index, element);
        this.executionTime = executionTime;
    }

    public Long getExecutionTime() {
        return executionTime;
    }


}