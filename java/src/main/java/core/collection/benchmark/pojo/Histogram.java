package core.collection.benchmark.pojo;

public class Histogram {

    private String collectionType;
    private MethodType methodType;
    private Integer index;
    private String element;
    private String histogramColumn;
    private String averageExecutionTime;


    public Histogram(final String collectionType,
                     final MethodType methodType,
                     final Integer index,
                     final String element,
                     final String histogramColumn,
                     final String averageExecutionTime)
    {
        this.collectionType = collectionType;
        this.methodType = methodType;
        this.index = index;
        this.element = element;
        this.histogramColumn = histogramColumn;
        this.averageExecutionTime = averageExecutionTime;
    }

    public String getElement() {
        return element;
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

    public String getAverageExecutionTime() {
        return String.format("%,.2f", this.getAverageExecutionTimeDouble());
    }

    public Double getAverageExecutionTimeDouble() {
        return Double.parseDouble(averageExecutionTime.replace(",", "."));
    }

    public void setHistogramColumn(final String histogramColumn) {
        this.histogramColumn = histogramColumn;
    }
}
