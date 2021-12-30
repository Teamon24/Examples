package benchmark.collection.pojo;

public class Histogram {

    private String collectionType;
    private MethodType methodType;
    private Integer index;
    private String histogramColumn;
    private Double averageExecutionTime;


    public Histogram(final String collectionType,
                     final MethodType methodType,
                     final Integer index,
                     final String histogramColumn,
                     final Double averageExecutionTime)
    {
        this.collectionType = collectionType;
        this.methodType = methodType;
        this.index = index;
        this.histogramColumn = histogramColumn;
        this.averageExecutionTime = averageExecutionTime;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public Integer getIndex() {
        return index;
    }

    public String getHistogramColumn() {
        return histogramColumn;
    }

    public Double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public void setHistogramColumn(final String histogramColumn) {
        this.histogramColumn = histogramColumn;
    }
}
