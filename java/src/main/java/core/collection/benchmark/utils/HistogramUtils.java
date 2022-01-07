package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;
import core.collection.benchmark.pojo.MethodType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

public abstract class HistogramUtils {

    public static final int DOUBLE_ACCURACY = 2;
    static LinkedList<String> marks = new LinkedList<>();
    static HashMap<Object, String> usedMarks = new HashMap<>();

    static {
        marks.add("*");
        marks.add("-");
        marks.add("o");
        marks.add("\\");
        marks.add("|");
        marks.add("+");
        marks.add("=");
        marks.add("#");
        marks.add("0");
    }

    public static <T> void checkSize(final Set<T> set) {
        HashSet<T> restCollectionTypes = new HashSet<>(set);
        restCollectionTypes.removeAll(usedMarks.keySet());
        int resultsSize = restCollectionTypes.size();
        int marksSize = marks.size();
        if (resultsSize > marksSize) {
            String template = "Not enough marks for collection types: marks amount - %s, but collection amount - %s";
            String message = String.format(template, marksSize, resultsSize);
            throw new RuntimeException(message);
        }
    }

    public static <T> HashMap<T, String> mapToMarks(Set<T> ts){
        HashMap<T, String> tAndMark = new LinkedHashMap<>();

        Iterator<T> iterator = ts.iterator();
        for (int i = 0; iterator.hasNext();) {
            T t = iterator.next();
            String usedMark = usedMarks.get(t);
            if (usedMark == null) {
                String mark = marks.get(i);
                usedMarks.put(t, mark);
                tAndMark.put(t, mark);
                marks.remove(i);
            } else {
                tAndMark.put(t, usedMark);
            }
        }
        return tAndMark;
    }

    public static List<Histogram> getHistograms(final List<AveragedMethodResult> results,
                                                final HashMap<String, String> collectionTypeAndMark)
    {
        Double minAverageTime = results.stream()
            .map(it -> getDigitsAfterDot(it.getAverageExecutionTime()))
            .filter(it -> it != 0)
            .min(Double::compareTo).orElseGet( () ->
                filterNotZeroAndFindBy(Stream::min, results, AveragedMethodResult::getAverageExecutionTime)
            );

        if (minAverageTime != 0) {
            List<Histogram> histograms = createEmptyColumnHistograms(results);
            setHistogramsColumns(collectionTypeAndMark, histograms);
            return histograms;
        } else {
            return createEmptyHistogramsObjects(results);
        }
    }


    protected static void setHistogramsColumns(final HashMap<String, String> collectionTypeAndMark,
                                               final List<Histogram> histograms)
    {
        List<Histogram> sortedByAverageTime =
            histograms.stream()
                .sorted(Comparator.comparing(Histogram::getAverageExecutionTimeDouble)).toList();

        for (int i = 0; i < sortedByAverageTime.size(); i++) {
            Histogram histogram = sortedByAverageTime.get(i);
            String mark = collectionTypeAndMark.get(histogram.getCollectionType());
            histogram.setHistogramColumn(mark.repeat(i));
        }
    }

    protected static List<Histogram> createEmptyHistogramsObjects(final List<AveragedMethodResult> results) {
        List<Histogram> histograms = results.stream().map(result -> {
            String collectionClass = result.getCollectionClass();
            return new Histogram(
                collectionClass,
                result.getMethodType(),
                result.getIndex(),
                "",
                "0.0");
        }).collect(Collectors.toList());
        return histograms;
    }

    public static <T> String getIndent(final T currentValue, final int maxValues) {
        return " ".repeat(maxValues - currentValue.toString().length());
    }

    public static List<Histogram> createEmptyColumnHistograms(final List<AveragedMethodResult> groupedByMethod)
    {
        List<Histogram> histograms = groupedByMethod.stream().map(result -> {
            String collectionClass = result.getCollectionClass();
            Double averageExecutionTime = result.getAverageExecutionTime();
            return new Histogram(
                collectionClass,
                result.getMethodType(),
                result.getIndex(),
                "",
                format(DOUBLE_ACCURACY, averageExecutionTime));
        }).collect(Collectors.toList());
        return histograms;
    }

    public static double getDigitsAfterDot(double d) {
        BigDecimal bd = new BigDecimal(d - Math.floor(d));
        bd = bd.setScale(4, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    public static Stream<Map.Entry<MethodType, List<Histogram>>> groupByMethod(final List<Histogram> histograms) {
        return histograms.stream()
            .collect(Collectors.groupingBy(Histogram::getMethodType))
            .entrySet()
            .stream();
    }

    public static <E> Double filterNotZeroAndFindBy(BiFunction<Stream<E>, Comparator<? super E>, Optional<E>> function,
                                                     final List<E> list,
                                                     Function<E, Double> fieldExtractor)
    {
        Optional<E> e = function.apply(
            list.stream().filter(it -> fieldExtractor.apply(it) != 0.0),
            Comparator.comparing(fieldExtractor)
        );
        return e.map(fieldExtractor).orElse(0.0);
    }

    public static String format(final int doubleAccuracy, final Double value) {
        return String.format("%." + doubleAccuracy + "f", value);
    }

    public static <T extends Comparable<T>> Stream<Map.Entry<T, List<Histogram>>> groupBy(
        final Function<Histogram, T> grouping,
        final List<Histogram> histograms)
    {
        return histograms.stream()
            .collect(Collectors.groupingBy(grouping))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey());
    }
}
