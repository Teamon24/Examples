package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;
import core.concurrency.ConcurrencyUtils;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.CollectionCreationUtils.set;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.ElementSupplier.getEachElement;

public class Tests {
    private static final int size = 500_000;
    private static final int testsAmount = 1000;

    private static final int logStep = testsAmount / 5;

    public static void main(String[] args) {
        Sequence<Integer> hashSetSequence = Sequence.intSequence();
        Sequence<Integer> treeSetSequence = Sequence.intSequence();
        Sequence<Integer> linkedHashSetSequence = Sequence.intSequence();
        Sequence<Integer> linkedListSequence = Sequence.intSequence();
        Sequence<Integer> arrayListSequence = Sequence.intSequence();
        Sequence<Integer> treeListSequence = Sequence.intSequence();

        Set<Integer> hashSet = set(HashSet.class, size, hashSetSequence::next);
        Set<Integer> treeSet = set(TreeSet.class, size, treeSetSequence::next);
        Set<Integer> linkedHashSet = set(LinkedHashSet.class, size, linkedHashSetSequence::next);
        List<Integer> linkedList = list(LinkedList.class, size, linkedListSequence::next);
        List<Integer> arrayList = list(ArrayList.class, size, arrayListSequence::next);
        List<Integer> treeList = list(TreeList.class, size, treeListSequence::next);

        boolean enableLog = true;
        List<List<Callable<List<MethodResult<Integer>>>>> methodResultsTasks = List.of(
            buildCallableTest(testsAmount, linkedList,    linkedListSequence).    getTests(enableLog, logStep),
            buildCallableTest(testsAmount, arrayList,     arrayListSequence).     getTests(enableLog, logStep),
            buildCallableTest(testsAmount, treeList,      treeListSequence).      getTests(enableLog, logStep),
            buildCallableTest(testsAmount, hashSet,       hashSetSequence).       getTests(enableLog, logStep),
            buildCallableTest(testsAmount, treeSet,       treeSetSequence).       getTests(enableLog, logStep),
            buildCallableTest(testsAmount, linkedHashSet, linkedHashSetSequence). getTests(enableLog, logStep)
        );

        List<List<MethodResult<Integer>>> methodResults = ConcurrencyUtils.getAll(
            methodResultsTasks.stream().flatMap(Collection::stream).toList()
        );

        Map<Boolean, List<MethodResult<Integer>>> partitionedResults = methodResults
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult<Integer>> resultsWithIndex =
            MethodResultGroupingUtils.averageByIndex(partitionedResults.get(true));

        List<AveragedMethodResult<Integer>> resultsWithNoIndex =
            MethodResultGroupingUtils.averageByMethod(partitionedResults.get(false));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
        HistogramWithIndexUtils.printHistogramNoIndex(resultsWithNoIndex);
    }

    public static <E> Predicate<E> each(int number) {
        final int[] counter = {0};
        return it -> {
            counter[0]++; return counter[0] % number == 0;
        };
    }

    private static <T> CallableCollectionTest<T> buildCallableTest(
        final int testsAmount,
        final Collection<T> collection,
        final Sequence<T> sequence
    ) {
        int period = 100;
        CollectionTest collectionTest = new CollectionTest.CollectionTestBuilder<T>()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(getEachElement(period, collection))
            .build();

        return new CallableCollectionTest<>(collectionTest);
    }
}
