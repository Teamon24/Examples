package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;
import utils.ConcurrencyUtils;
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
import java.util.stream.Stream;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.CollectionCreationUtils.set;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.ElementSupplier.periodicallyFrom;

public class Tests {
    private static final int size = 10_000;
    private static final int testsAmount = 50000;

    private static final int logStep = testsAmount / 2;

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

        final int period = size/10;
        boolean enableLog = true;
        List<List<Callable<List<MethodResult<Integer>>>>> listMethodResultsTasks = List.of(
            test(testsAmount, linkedList, linkedListSequence, period).getListMethodTests(enableLog, logStep),
            test(testsAmount, arrayList,  arrayListSequence,  period).getListMethodTests(enableLog, logStep),
            test(testsAmount, treeList,   treeListSequence,   period).getListMethodTests(enableLog, logStep)
        );

        List<List<Callable<List<MethodResult<Integer>>>>> setMethodResultsTasks = List.of(
            test(testsAmount, hashSet,       Sequence.randomFrom(hashSet),       period).getSetMethodTests(enableLog, logStep),
            test(testsAmount, treeSet,       Sequence.randomFrom(treeSet),       period).getSetMethodTests(enableLog, logStep),
            test(testsAmount, linkedHashSet, Sequence.randomFrom(linkedHashSet), period).getSetMethodTests(enableLog, logStep)
        );

        List<List<MethodResult<Integer>>> methodResults = ConcurrencyUtils.getAll(
            Stream.of(listMethodResultsTasks, setMethodResultsTasks)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
        );

        Map<Boolean, List<MethodResult<Integer>>> partitionedResults = methodResults
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));

        List<AveragedMethodResult<Integer>> resultsWithIndex =
            MethodResultGroupingUtils.averageByIndex(partitionedResults.get(true));

        List<AveragedMethodResult<Integer>> resultsWithNoIndex =
            MethodResultGroupingUtils.averageByElement(partitionedResults.get(false));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
        HistogramWithIndexUtils.printHistogramNoIndex(resultsWithNoIndex.stream().filter(by(period)).collect(Collectors.toList()));
    }

    private static Predicate<AveragedMethodResult<Integer>> by(int period) {
        final int[] counter = {0};
        return it -> {
            boolean filtered = counter[0] % period == 0;
            counter[0] += 1;
            return filtered;
        };
    }

    private static <E> CallableCollectionTest<E> test(
        final int testsAmount,
        final Collection<E> collection,
        final Sequence<E> sequence,
        final int period
    ) {
        CollectionTest<E> collectionTest = new CollectionTest.CollectionTestBuilder<E>()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(periodicallyFrom(collection, period))
            .build();

        return new CallableCollectionTest<>(collectionTest);
    }
}
