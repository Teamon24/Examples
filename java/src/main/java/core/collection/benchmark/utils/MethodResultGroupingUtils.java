package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MethodResultGroupingUtils {

    public static <F, E> List<AveragedMethodResult<E>> averageBy(
        List<MethodResult<E>> testResults,
        Function<MethodResult<E>, F> getter,
        TriFunction<Map.Entry<F, Double>, String, MethodType, AveragedMethodResult<E>> averageResultCreation
    ) {
        if (testResults.isEmpty()) return Collections.emptyList();

        Map<String, Map<MethodType, Map<F, Double>>> averageExecutionTime = testResults.stream().collect(
            Collectors.groupingBy(
                MethodResult::getCollectionClass,
                Collectors.groupingBy(
                    MethodResult::getMethodType,
                    Collectors.groupingBy(
                        getter, Collectors.averagingLong(MethodResult::getExecutionTime)))));


        List<AveragedMethodResult<E>> collect = averageExecutionTime.entrySet()
            .stream()
            .flatMap(entry -> getStream(entry)
                .flatMap(entry2 -> getEntryStream(entry2)
                    .flatMap(entry3 -> {
                            String collectionType = entry.getKey();
                            MethodType methodType = entry2.getKey();
                            return Stream.of(averageResultCreation.apply(entry3, collectionType, methodType));
                        }
                    )
                )
            )
            .collect(Collectors.toList());

        return collect;
    }

    public static <E> List<AveragedMethodResult<E>> averageByElement(List<MethodResult<E>> testResults) {
        Function<MethodResult<E>, Object> getElement = MethodResult::getElement;
        return averageBy(
            testResults,
            getElement,
            (Map.Entry<Object, Double> entry,
             String collection,
             MethodType method) -> new AveragedMethodResult<>(collection, method, null, (E) entry.getKey(), entry.getValue()));
    }

    public static <E> List<AveragedMethodResult<E>> averageByIndex(List<MethodResult<E>> testResults) {
        Function<MethodResult<E>, Object> fieldExtractor = MethodResult::getIndex;
        return averageBy(
            testResults,
            fieldExtractor,
            (Map.Entry<Object, Double> entry,
             String collection,
             MethodType method) -> new AveragedMethodResult<>(collection, method, (Integer) entry.getKey(), null , entry.getValue()));
    }

    public static <E> List<AveragedMethodResult<E>> averageByMethod(List<MethodResult<E>> testResults) {
        if (testResults.isEmpty()) return Collections.emptyList();

        Map<String, Map<MethodType, Double>> averageExecutionTime = testResults.stream().collect(
            Collectors.groupingBy(
                MethodResult::getCollectionClass,
                Collectors.groupingBy(
                    MethodResult::getMethodType, Collectors.averagingLong(MethodResult::getExecutionTime))));


        List<AveragedMethodResult<E>> collect = averageExecutionTime.entrySet()
            .stream()
            .flatMap(collectionMethodsAndAverageTime -> getStream1(collectionMethodsAndAverageTime)
                .flatMap(methodAndAverageTime -> {
                    String collectionType = collectionMethodsAndAverageTime.getKey();
                    return Stream.of(MethodResultGroupingUtils.<E>getAveragedResult(collectionType, methodAndAverageTime));
                }))
            .collect(Collectors.toList());

        return collect;
    }

    private static <T> Stream<Map.Entry<T, Double>>
    getEntryStream(final Map.Entry<MethodType, Map<T, Double>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static <T> Stream<Map.Entry<MethodType, Map<T, Double>>>
    getStream(final Map.Entry<String, Map<MethodType, Map<T, Double>>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static Stream<Map.Entry<MethodType, Double>>
    getStream1(final Map.Entry<String, Map<MethodType, Double>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static <E> AveragedMethodResult<E> getAveragedResult(
        String collectionType, final Map.Entry<MethodType, Double> methodAndAverageTime
    ) {
        MethodType methodType = methodAndAverageTime.getKey();
        Double averagedTime = methodAndAverageTime.getValue();
        return new AveragedMethodResult<>(collectionType, methodType, null, null, averagedTime);
    }

}
