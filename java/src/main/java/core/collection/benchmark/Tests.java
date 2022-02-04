package core.collection.benchmark;

import core.collection.benchmark.core.CollectionTestBuilder;
import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.IntSequence;
import core.collection.benchmark.utils.MethodsTestsTasks;
import core.collection.benchmark.utils.Sequence;
import org.apache.commons.collections4.list.TreeList;
import utils.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.CollectionCreationUtils.createList;
import static core.collection.benchmark.utils.CollectionCreationUtils.createSet;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.ElementSupplier.periodicallyFrom;
import static core.collection.benchmark.utils.MethodResultGroupingUtils.averageByElement;
import static core.collection.benchmark.utils.MethodResultGroupingUtils.averageByIndex;

public class Tests {
    private static final int size = 15_000;
    private static final int testsAmount = 75_000;

    private static final boolean enableLog = true;
    private static final int logStep = testsAmount / 2;

    public static void main(String[] args) {

        List<MethodsTestsTasks<Integer>> methodsTestsTasks =
                getMethodsTestsTasks(
                    LinkedList.class, ArrayList.class, TreeList.class,
                    HashSet.class, LinkedHashSet.class, TreeSet.class);

        List<MethodResult<Integer>> methodResults = ConcurrencyUtils
            .getAll(methodsTestsTasks)
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());


        Map<Boolean, List<MethodResult<Integer>>> partitionedResults = methodResults
            .stream()
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));

        List<AveragedMethodResult<Integer>> resultsWithIndex = averageByIndex(partitionedResults.get(true));
        List<AveragedMethodResult<Integer>> resultsWithNoIndex = averageByElement(partitionedResults.get(false));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
        HistogramWithIndexUtils.printHistogramNoIndex(resultsWithNoIndex);
    }

    private static List<MethodsTestsTasks<Integer>> getMethodsTestsTasks(Class<? extends Collection>... collectionClasses) {

        final int period = (int) (size * 0.2);
        int limit = size * 2;
        List<MethodsTestsTasks<Integer>> tests = new ArrayList<>();
        List<MethodsTestsTasks<Integer>> tempContainer = new ArrayList<>();
        for (Class<?> collectionClass : collectionClasses) {
            IntSequence sequence = IntSequence.create();

            if (List.class.isAssignableFrom(collectionClass)) {
                tempContainer = listTest(testsAmount,
                    createList(collectionClass, size, sequence::next),
                    sequence,
                    period,
                    enableLog,
                    logStep);
            }

            if (Set.class.isAssignableFrom(collectionClass)) {
                tempContainer = setTest(testsAmount,
                    createSet(collectionClass, size, sequence::next),
                    sequence.fromLast(period, limit),
                    period,
                    enableLog,
                    logStep);
            }

            tests.addAll(tempContainer);

        }
        return tests;
    }

    private static <E> List<MethodsTestsTasks<E>> setTest(
        final int testsAmount,
        final Collection<E> collection,
        final Sequence<E> sequence,
        final int period,
        boolean enableLog,
        final int logStep
    ) {
        return new CollectionTestBuilder<E>()
            .sets()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(periodicallyFrom(collection, period))
            .build()
            .callables()
            .getMethodsTests(enableLog, logStep);
    }

    private static <E> List<MethodsTestsTasks<E>> listTest(
        final int testsAmount,
        final Collection<E> collection,
        final Sequence<E> sequence,
        final int period,
        boolean enableLog,
        final int logStep
    ) {

        return new CollectionTestBuilder<E>()
            .lists()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(periodicallyFrom(collection, period))
            .indexSupplier(IndexSuppliers.supplyThreeIndexes())
            .build()
            .callables()
            .getMethodsTests(enableLog, logStep);
    }
}
