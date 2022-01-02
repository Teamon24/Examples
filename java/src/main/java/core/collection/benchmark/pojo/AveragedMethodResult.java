package core.collection.benchmark.pojo;

public class AveragedMethodResult extends Result {

    private Double averageExecutionTime;

    public AveragedMethodResult(final String listType,
                                final MethodType methodType,
                                final Integer index,
                                final Object element,
                                final Double averageExecutionTime)
    {
        super(listType, methodType, index, element);
        this.averageExecutionTime = averageExecutionTime;
    }

    public Double getAverageExecutionTime() {
        return averageExecutionTime;
    }
}
