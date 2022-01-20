package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.ElementSupplier;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;
import core.concurrency.ConcurrencyUtils;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.ElementSupplier.getElementAndDiscard;

public class RandomIndexTests {
    private static final int size = 5_000;
    private static final int testsAmount = 50_000;

    private static final int logStep = testsAmount / 5;

    public static void main(String[] args) {
        Sequence<Integer> linkedListSequence = Sequence.intSequence();
        Sequence<Integer> arrayListSequence = Sequence.intSequence();
        Sequence<Integer> treeListSequence = Sequence.intSequence();

        List<Integer> linkedList = list(LinkedList.class, size, linkedListSequence::next);
        List<Integer> arrayList = list(ArrayList.class, size, arrayListSequence::next);
        List<Integer> treeList = list(TreeList.class, size, treeListSequence::next);

        boolean enableLog = true;
        List<List<Callable<List<MethodResult<Integer>>>>> methodResultsTasks = List.of(
            collectionTest(testsAmount, linkedList, linkedListSequence).getRandomIndexTests(enableLog, logStep),
            collectionTest(testsAmount, arrayList, arrayListSequence).getRandomIndexTests(enableLog, logStep),
            collectionTest(testsAmount, treeList, treeListSequence).getRandomIndexTests(enableLog, logStep)
        );

        List<List<MethodResult<Integer>>> methodResults = ConcurrencyUtils.getAll(
            methodResultsTasks.stream().flatMap(Collection::stream).toList()
        );

        Map<Boolean, List<MethodResult<Integer>>> partitionedResults = methodResults
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult<Integer>> resultsWithIndex =
            MethodResultGroupingUtils
                .averageByIndex(partitionedResults.get(true))
                .stream()
                .filter(each(size / 5))
                .toList();


        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
    }


    public static <E> Predicate<AveragedMethodResult<E>> each(int number) {
        return it -> it.getIndex() % number == 0;
    }

    private static <T> CallableCollectionTest<T> collectionTest(
        final int testsAmount,
        final Collection<T> collection,
        final Sequence<T> sequence
    ) {
        CollectionTest collectionTest = new CollectionTest.CollectionTestBuilder<T>()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(getElementAndDiscard(collection))
            .build();

        return new CallableCollectionTest<>(collectionTest);
    }
}
