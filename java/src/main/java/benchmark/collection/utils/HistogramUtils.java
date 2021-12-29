package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.Histogram;
import benchmark.collection.pojo.MethodType;
import thread.ex1.IntegerUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HistogramUtils {

    private static LinkedList<String> marks = new LinkedList<>();

    private static final double HISTOGRAM_COLUMN_LIMIT = 30.0;

    static {
        marks.add("*");
        marks.add("#");
        marks.add("+");
        marks.add("=");
        marks.add("|");
        marks.add("\\");
        marks.add("o");
        marks.add("0");
    }

    public static void printHistogramForLists(List<AveragedMethodResult> results) {
        Set<String> collectionTypes = StreamUtils.getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapTypesToMarks(collectionTypes);


        Double minAverageTime = filterNotZeroAndFindBy(Stream::min, results, AveragedMethodResult::getAverageExecutionTime);
        Double maxAverageTime = filterNotZeroAndFindBy(Stream::max, results, AveragedMethodResult::getAverageExecutionTime);
        List<Histogram> histograms = createHistogramsObjects(collectionTypeAndMark, results, minAverageTime, maxAverageTime);

        Integer lengthOfMaxIndex = getLengthOfMaxIndex(results);
        Integer lengthOfLongestCollectionName = MaxUtils.getLongestCollectionName(collectionTypes).length();
        int lengthOfLongestHistogram = MaxUtils.getLongestHistogramColumn(histograms).length();
        Set<MethodType> methodTypes = StreamUtils.getUniques(results, AveragedMethodResult::getMethodType);
        int lengthOfLongestMethodName = MaxUtils.getLongestMethodName(methodTypes).length();

        int count = 2 * (
                lengthOfLongestMethodName +
                lengthOfMaxIndex +
                lengthOfLongestCollectionName +
                lengthOfLongestCollectionName);

        StringBuilder builder = new StringBuilder();
        groupByMethod(histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                appendFirstLine(count, builderForMethod);
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

    private static void appendFirstLine(final int count, final StringBuilder builderForMethod) {
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

    private static String getHistogramColumnIndent(final Histogram histogram, final int lengthOfLongestHistogram) {
        return " ".repeat(lengthOfLongestHistogram - histogram.getHistogramColumn().length());
    }

    private static String getCollectionTypeIndent(final String collectionType, final Integer lengthOfLongestCollectionName) {
        return " ".repeat(lengthOfLongestCollectionName - collectionType.length());
    }

    private static Integer getLengthOfMaxIndex(final List<AveragedMethodResult> results) {
        Integer maximalIndex = results.stream().max(Comparator.comparing(AveragedMethodResult::getIndex)).get().getIndex();
        Integer lengthOfMaxIndex = IntegerUtils.countDigits(maximalIndex);
        return lengthOfMaxIndex;
    }

    private static String getSeparationLine(String separationSymbol, int count) {
        return separationSymbol.repeat(count);
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

    private static <E> Double filterNotZeroAndFindBy(BiFunction<Stream<E>, Comparator<? super E>, Optional<E>> function,
                                                     final List<E> list,
                                                     Function<E, Double> fieldExtractor)
    {
        E e = function.apply(
            list.stream().filter(it -> fieldExtractor.apply(it) != 0.0),
            Comparator.comparing(fieldExtractor)
        ).get();
        return fieldExtractor.apply(e);
    }

    private static List<Histogram> createHistogramsObjects(final HashMap<String, String> collectionTypeAndMark,
                                                           final List<AveragedMethodResult> groupedByMethod,
                                                           final Double minAverageTime,
                                                           final Double maxAverageTime)
    {
        List<Histogram> histograms = groupedByMethod.stream().map(result -> {
            String collectionClass = result.getCollectionClass();
            Double averageExecutionTime = result.getAverageExecutionTime();
            return new Histogram(
                collectionClass,
                result.getMethodType(),
                result.getIndex(),
                getHistogramColumn(collectionClass, collectionTypeAndMark, averageExecutionTime, minAverageTime, maxAverageTime),
                averageExecutionTime);
        }).collect(Collectors.toList());
        return histograms;
    }

    private static Stream<Map.Entry<Integer, List<Histogram>>> groupByIndex(final Map.Entry<MethodType, List<Histogram>> histogramByIndex) {
        return histogramByIndex.getValue().stream().collect(Collectors.groupingBy(Histogram::getIndex))
            .entrySet().stream().sorted(Map.Entry.comparingByKey());
    }

    private static Stream<Map.Entry<MethodType, List<Histogram>>> groupByMethod(final List<Histogram> histograms) {
        return histograms.stream()
            .collect(Collectors.groupingBy(Histogram::getMethodType)).entrySet().stream();
    }

    private static String getHistogramColumn(final String collectionClass,
                                             final HashMap<String, String> collectionTypeAndMark,
                                             final Double averageExecutionTime,
                                             final Double minAverageTime,
                                             final Double maxAverageTime)
    {
        String mark = collectionTypeAndMark.get(collectionClass);
        if (averageExecutionTime == 0) return "";

        if (maxAverageTime / (minAverageTime / 2) > HISTOGRAM_COLUMN_LIMIT) {
            double v = HISTOGRAM_COLUMN_LIMIT / maxAverageTime * averageExecutionTime;
            return mark.repeat((int)Math.round(v));
        }

        return mark.repeat((int)(averageExecutionTime / minAverageTime));
    }

    private static HashMap<String, String> mapTypesToMarks(Set<String> collectionTypes){
        HashMap<String, String> classToSign = new LinkedHashMap<>();

        Iterator<String> iterator = collectionTypes.iterator();
        for (int i = 0; i < marks.size() && iterator.hasNext(); i++) {
            classToSign.put(iterator.next(), marks.get(i));
        }
        return classToSign;
    }

    private static void checkSize(final Set<String> averagedMethodResults) {
        int resultsSize = averagedMethodResults.size();
        int marksSize = marks.size();
        if (resultsSize > marksSize) {
            String template = "Not enough marks for collection types: marks amount - %s, but collection amount - %s";
            String.format(template, marksSize, resultsSize);
            throw new RuntimeException(template);
        }
    }
}
