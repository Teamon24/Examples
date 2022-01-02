package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;
import core.collection.benchmark.pojo.MethodType;
import core.concurrency.thread.ex1.utils.IntegerUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HistogramWithIndexUtils extends HistogramUtils {


    public static void printHistogram(List<AveragedMethodResult> results, Integer maxHistogramLength) {
        maxHistogramColumnLength = maxHistogramLength;
        printHistogram(results);
    }

    public static void printHistogram(List<AveragedMethodResult> results) {
        Set<String> collectionTypes = StreamUtils.getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapTypesToMarks(collectionTypes);
        List<Histogram> histograms = getHistograms(results, collectionTypeAndMark);

        Integer lengthOfMaxIndex = getLengthOfMaxIndex(results);
        Integer lengthOfLongestCollectionName = MaxUtils.getLongestCollectionName(collectionTypes).length();
        int lengthOfLongestHistogram = MaxUtils.getLongestHistogramColumn(histograms).length();
        Set<MethodType> methodTypes = StreamUtils.getUniques(results, AveragedMethodResult::getMethodType);
        int lengthOfLongestMethodName = MaxUtils.getLongestMethodName(methodTypes).length();

        int count = (
            lengthOfLongestMethodName +
                lengthOfMaxIndex +
                lengthOfLongestCollectionName +
                lengthOfLongestCollectionName);

        StringBuilder builder = new StringBuilder();
        groupByMethod(histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                appendFirstLine(builderForMethod, lengthOfLongestMethodName);
                groupByIndex(histogramsByMethod)
                    .forEach(histogramsByIndex -> {
                        final StringBuilder builderForIndex = new StringBuilder();
                        for (Histogram histogram : histogramsByIndex.getValue()) {
                            Integer index = histogramsByIndex.getKey();
                            appendHistogram(
                                builderForIndex, index, histogram,
                                lengthOfMaxIndex,
                                lengthOfLongestCollectionName,
                                lengthOfLongestHistogram,
                                lengthOfLongestMethodName);
                        }
                        appendIndexGroup(builderForMethod, builderForIndex, lengthOfLongestMethodName);
                    });
                appendMethodGroup(builder, builderForMethod);});

        System.out.println(builder);
    }

    private static void appendIndexGroup(final StringBuilder builderForMethod,
                                         final StringBuilder builderForIndex,
                                         final int lengthOfLongestMethodName) {
        builderForMethod
            .append(builderForIndex)
            .append(getSeparationLine("-", lengthOfLongestMethodName))
            .append("\n");
    }

    private static void appendMethodGroup(final StringBuilder builder, final StringBuilder builderForMethod) {
        builder
            .append(builderForMethod)
            .append("\n");
    }

    private static void appendFirstLine(final StringBuilder builderForMethod, final int count) {
        builderForMethod.append(getSeparationLine("-", count)).append("\n");
    }

    private static void appendHistogram(final StringBuilder builderForIndex,
                                        final Integer index,
                                        final Histogram histogram,
                                        final Integer lengthOfMaxIndex,
                                        final Integer lengthOfLongestCollectionName,
                                        final int lengthOfLongestHistogram,
                                        final int lengthOfLongestMethodName)
    {
        int counter = 0;
        String collectionType = histogram.getCollectionType();
        String methodTypeName = histogram.getMethodType().getValue();
        String histogramColumnIndent = getHistogramColumnIndent(histogram, lengthOfLongestHistogram);
        String collectionTypeIndent = getCollectionTypeIndent(collectionType, lengthOfLongestCollectionName);
        String indexIndent;
        if (counter == 0) {
            builderForIndex
                .append(" ".repeat(lengthOfLongestMethodName - methodTypeName.length()))
                .append(methodTypeName)
                .append(" ");

            indexIndent = getIndexIndent(lengthOfMaxIndex, index) + index;
            counter++;
        } else {
            builderForIndex.append(" ".repeat(lengthOfLongestMethodName)).append(" ");
            indexIndent = " ".repeat(lengthOfMaxIndex);
        }
        appendIndexLine(builderForIndex, histogram, indexIndent, collectionTypeIndent, histogramColumnIndent);
        builderForIndex.append("\n");
    }

    private static Integer getLengthOfMaxIndex(final List<AveragedMethodResult> results) {
        Integer maximalIndex = results.stream().max(Comparator.comparing(AveragedMethodResult::getIndex)).get().getIndex();
        Integer lengthOfMaxIndex = IntegerUtils.countDigits(maximalIndex);
        return lengthOfMaxIndex;
    }

    private static StringBuilder appendIndexLine(final StringBuilder builderForIndex,
                                                 final Histogram it,
                                                 final String indexIndent,
                                                 final String collectionTypeIndent,
                                                 final String histogramColumnIndent) {
        return builderForIndex
            .append(indexIndent).append(" ")
            .append(collectionTypeIndent).append(it.getCollectionType()).append(" ")
            .append(it.getHistogramColumn()).append(histogramColumnIndent).append(" ")
            .append(it.getAverageExecutionTime()).append(" ");
    }

    private static String getIndexIndent(final Integer lengthOfMaxIndex, final Integer index) {
        return " ".repeat(lengthOfMaxIndex - IntegerUtils.countDigits(index));
    }

    private static Stream<Map.Entry<Integer, List<Histogram>>> groupByIndex(final Map.Entry<MethodType, List<Histogram>> histogramByIndex) {
        return histogramByIndex.getValue().stream().collect(Collectors.groupingBy(Histogram::getIndex))
            .entrySet().stream().sorted(Map.Entry.comparingByKey());
    }
}
