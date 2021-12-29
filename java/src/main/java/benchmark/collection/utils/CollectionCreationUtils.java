package benchmark.collection.utils;

import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class CollectionCreationUtils {

    private static Random random = new Random();

    public static <T> LinkedList<T> fillLinkedList(final int size, final Supplier<T> elementSupplier) {
        return (LinkedList) fillCollection(size, new LinkedList<>(), elementSupplier);
    }

    public static <T> ArrayList<T> fillArrayList(final int size, final Supplier<T> supplier) {
        return (ArrayList<T>) fillCollection(size, new ArrayList<>(), supplier);
    }

    public static <T> TreeList<T> fillTreeList(final int size, final Supplier<T> supplier) {
        return (TreeList<T>) fillCollection(size, new TreeList<>(), supplier);
    }

    public static <T> HashSet<T> fillHashSet(final int size, final Supplier<T> supplier) {
        return (HashSet) fillCollection(size, new HashSet<>(), supplier);
    }

    public static <T> TreeSet<T> fillTreeSet(final int size, final Supplier<T> supplier) {
        return (TreeSet) fillCollection(size, new TreeSet<>(), supplier);
    }

    public static <T> LinkedHashSet<T> fillLinkedHashSet(final int size, final Supplier<T> supplier) {
        return (LinkedHashSet) fillCollection(size, new LinkedHashSet<>(), supplier);
    }

    public static <E> Collection<E> fillCollection(
        int size,
        Collection<E> collection,
        Supplier<E> getElement)
    {
        System.out.println(String.format("Filling %s (%s)", collection.getClass().getSimpleName(), size));
        for (int i = 0; i < size; i++) {
            E e = getElement.get();
            collection.add(e);
        }
        System.out.println(String.format("%s is full, size: %s", collection.getClass().getSimpleName(), collection.size()));

        return collection;
    }

    public static Integer getRandomIndex(int size) {
        return random.nextInt(size - 1);
    }

    public static <T> List<T> of(T...elements) {
        return Arrays.stream(elements).collect(Collectors.toList());
    }

}
