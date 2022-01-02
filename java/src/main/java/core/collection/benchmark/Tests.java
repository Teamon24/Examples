package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.HistogramWithNoIndexUtils;
import core.collection.benchmark.utils.Sequence;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.PrintUtils;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.collection.benchmark.utils.CollectionCreationUtils.fillArrayList;
import static core.collection.benchmark.utils.CollectionCreationUtils.hashSet;
import static core.collection.benchmark.utils.CollectionCreationUtils.linkedHashSet;
import static core.collection.benchmark.utils.CollectionCreationUtils.fillLinkedList;
import static core.collection.benchmark.utils.CollectionCreationUtils.fillTreeList;
import static core.collection.benchmark.utils.CollectionCreationUtils.treeSet;
import static core.collection.benchmark.utils.ElementSupplier.*;

public class Tests {
    private static final int size = 200_000;
    private static final int testsAmount = 50;

    public static void main(String[] args) {
        Sequence<Integer> intSequence = Sequence.first(0).init((it -> it = it+1));
        Sequence<Double> doubleSequence = Sequence.first(0.0).init((it -> it = it+1));

        Set<Integer> hashSet = hashSet(size, intSequence::next);
        Set<Integer> treeSet = treeSet(size, intSequence::next);
        Set<Integer> linkedHashSet = linkedHashSet(size, intSequence::next);

        LinkedList<Double> linkedList = fillLinkedList(size, doubleSequence::next);
        ArrayList<Integer> arrayList = fillArrayList(size, intSequence::next);
        TreeList<Double> treeList = fillTreeList(size, doubleSequence::next);

        int logStep = 100;

        Map<Boolean, List<MethodResult>> partitionedResults = Stream.of(
                collectionTest(linkedList).   testList(sequenceElementSupplier(doubleSequence), random(linkedList),    logStep),
                collectionTest(arrayList).    testList(sequenceElementSupplier(intSequence),    random(arrayList),     logStep),
                collectionTest(treeList).     testList(sequenceElementSupplier(doubleSequence), random(treeList),      logStep),
                collectionTest(hashSet).      testSet( sequenceElementSupplier(intSequence),    random(hashSet),       logStep),
                collectionTest(treeSet).      testSet( sequenceElementSupplier(intSequence),    random(treeSet),       logStep),
                collectionTest(linkedHashSet).testSet( sequenceElementSupplier(intSequence),    random(linkedHashSet), logStep)
            )
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult> resultsWithIndex = MethodResultGroupingUtils.averageByIndex(partitionedResults.get(true));
        List<AveragedMethodResult> resultsWithNoIndex = MethodResultGroupingUtils.averageByMethod(partitionedResults.get(false));

        PrintUtils.printTable(
            resultsWithIndex.stream().sorted(
                Comparator
                    .comparing(AveragedMethodResult::getMethodType)
                    .thenComparing(AveragedMethodResult::getIndex)
                    .thenComparing(AveragedMethodResult::getAverageExecutionTime)).collect(Collectors.toList()));

        PrintUtils.printTable(
            resultsWithNoIndex.stream().sorted(
            Comparator
                .comparing(AveragedMethodResult::getMethodType)
                .thenComparing(AveragedMethodResult::getAverageExecutionTime)).collect(Collectors.toList()));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex, 30);
        HistogramWithNoIndexUtils.printHistogram(resultsWithNoIndex, 30);
    }

    private static <T> CollectionTest<T> collectionTest(final Collection<T> collection) {
        return new CollectionTest<>(testsAmount, collection);
    }

    private static <E> Supplier<E> random(final Collection<E> collection) {
        return getRandomElementAndForgetHimFrom(collection);
    }
}
