package benchmark.collection;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.MethodResult;
import benchmark.collection.utils.HistogramWithIndexUtils;
import benchmark.collection.utils.HistogramWithNoIndexUtils;
import benchmark.collection.utils.Sequence;
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

import static benchmark.collection.utils.CollectionCreationUtils.fillArrayList;
import static benchmark.collection.utils.CollectionCreationUtils.hashSet;
import static benchmark.collection.utils.CollectionCreationUtils.linkedHashSet;
import static benchmark.collection.utils.CollectionCreationUtils.fillLinkedList;
import static benchmark.collection.utils.CollectionCreationUtils.fillTreeList;
import static benchmark.collection.utils.CollectionCreationUtils.treeSet;
import static benchmark.collection.utils.ElementSupplier.getRandomElementAndForgetHimFrom;
import static benchmark.collection.utils.ElementSupplier.sequence;
import static benchmark.collection.utils.MethodResultGroupingUtils.averageByIndex;
import static benchmark.collection.utils.MethodResultGroupingUtils.averageByMethod;
import static benchmark.collection.utils.PrintUtils.printTable;

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
                collectionTest(linkedList).testList(sequence(doubleSequence), random(linkedList), logStep),
                collectionTest(arrayList).testList(sequence(intSequence), random(arrayList), logStep),
                collectionTest(treeList).testList(sequence(doubleSequence), random(treeList), logStep),
                collectionTest(hashSet).testSet(sequence(intSequence), random(hashSet), logStep),
                collectionTest(treeSet).testSet(sequence(intSequence), random(treeSet), logStep),
                collectionTest(linkedHashSet).testSet(sequence(intSequence), random(linkedHashSet), logStep)
            )
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult> resultsWithIndex = averageByIndex(partitionedResults.get(true));
        List<AveragedMethodResult> resultsWithNoIndex = averageByMethod(partitionedResults.get(false));

        printTable(
            resultsWithIndex.stream().sorted(
                Comparator
                    .comparing(AveragedMethodResult::getMethodType)
                    .thenComparing(AveragedMethodResult::getIndex)
                    .thenComparing(AveragedMethodResult::getAverageExecutionTime)).collect(Collectors.toList()));

        printTable(
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
