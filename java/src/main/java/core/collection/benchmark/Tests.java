package core.collection.benchmark;

import core.collection.benchmark.core.CollectionTestBuilder;
import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.CollectionCreationUtils;
import core.collection.benchmark.utils.CollectionSuppliers;
import core.collection.benchmark.utils.ElementSupplier;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.IntSequence;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.MethodsTestsTasks;
import core.collection.benchmark.utils.Sequence;
import org.apache.commons.collections4.list.TreeList;
import utils.CallableUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Tests {
    private static final int size = 100_000;
    private static final int testsAmount = 1000;

    private static final boolean enableLog = true;
    private static final int logStep = testsAmount / 2;

    public static void main(String[] args) {

        List<MethodsTestsTasks<Integer>> methodsTestsTasks =
            getMethodsTestsTasks(
                ArrayList.class,
                TreeList.class,
                LinkedList.class/*,
                HashSet.class,
                LinkedHashSet.class,
                TreeSet.class*/
            );

        List<MethodResult<Integer>> methodResults = CallableUtils
            .getAll(methodsTestsTasks, 4)
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        Map<Boolean, List<MethodResult<Integer>>> resultsByIndex = methodResults
            .stream()
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));

        List<AveragedMethodResult<Integer>> resultsWithIndex = MethodResultGroupingUtils.averageByIndex(takeWithIndex(resultsByIndex));
        List<AveragedMethodResult<Integer>> resultsWithNoIndex = MethodResultGroupingUtils.averageByElement(takeWithNoIndex(resultsByIndex));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
        HistogramWithIndexUtils.printHistogramNoIndex(resultsWithNoIndex);
    }

    private static List<MethodResult<Integer>> takeWithIndex(
        Map<Boolean, List<MethodResult<Integer>>> resultsByIndex
    ) {
        return resultsByIndex.get(true);
    }

    private static List<MethodResult<Integer>> takeWithNoIndex(
        Map<Boolean, List<MethodResult<Integer>>> resultsByIndex
    ) {
        return resultsByIndex.get(false);
    }

    private static List<MethodsTestsTasks<Integer>> getMethodsTestsTasks(Class<? extends Collection>... collectionClasses) {

        final int period = (int) (size * 0.2);
        int limit = size * 2;
        List<MethodsTestsTasks<Integer>> tests = new ArrayList<>();
        for (Class<?> collectionClass : collectionClasses) {
            IntSequence elementsSupplier = IntSequence.create();

            if (List.class.isAssignableFrom(collectionClass)) {
                tests.addAll(
                    listTests(
                        testsAmount,
                        CollectionCreationUtils.createList(collectionClass, size, elementsSupplier::next),
                        elementsSupplier,
                        period,
                        enableLog,
                        logStep));

                continue;
            }

            if (Set.class.isAssignableFrom(collectionClass)) {
                tests.addAll(
                    setTests(
                        testsAmount,
                        CollectionCreationUtils.createSet(collectionClass, size, elementsSupplier::next),
                        elementsSupplier.fromLast(period, limit),
                        period,
                        enableLog,
                        logStep));
                continue;
            }

            String format = "There is no test creation logic for collection type %s";
            String message = String.format(format, collectionClass.getSimpleName());
            throw new RuntimeException(message);
        }
        return tests;
    }

    private static <E> List<MethodsTestsTasks<E>> setTests(
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
            .collectionSupplier(CollectionSuppliers.newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(ElementSupplier.periodicallyFrom(collection, period))
            .build()
            .callables()
            .getMethodsTests(enableLog, logStep);
    }

    private static <E> List<MethodsTestsTasks<E>> listTests(
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
            .collectionSupplier(CollectionSuppliers.newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(ElementSupplier.periodicallyFrom(collection, period))
            .indexSupplier(IndexSuppliers.supplyThreeIndexes())
            .build()
            .callables()
            .getMethodsTests(enableLog, logStep);
    }
}
