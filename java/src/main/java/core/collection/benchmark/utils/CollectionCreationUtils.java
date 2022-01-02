package core.collection.benchmark.utils;

import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.function.Supplier;

public final class CollectionCreationUtils {

    public static <T> LinkedList<T> fillLinkedList(final int size, final Supplier<T> elementSupplier) {
        return (LinkedList<T>) fillCollection(size, new LinkedList<>(), elementSupplier);
    }

    public static <T> ArrayList<T> fillArrayList(final int size, final Supplier<T> supplier) {
        return (ArrayList<T>) fillCollection(size, new ArrayList<>(), supplier);
    }

    public static <T> TreeList<T> fillTreeList(final int size, final Supplier<T> supplier) {
        return (TreeList<T>) fillCollection(size, new TreeList<>(), supplier);
    }

    public static <T> HashSet<T> hashSet(final int size, final Supplier<T> supplier) {
        return (HashSet<T>) fillCollection(size, new HashSet<>(), supplier);
    }

    public static <T> TreeSet<T> treeSet(final int size, final Supplier<T> supplier) {
        return (TreeSet<T>) fillCollection(size, new TreeSet<>(), supplier);
    }

    public static <T> LinkedHashSet<T> linkedHashSet(final int size, final Supplier<T> supplier) {
        return (LinkedHashSet<T>) fillCollection(size, new LinkedHashSet<>(), supplier);
    }

    public static <E> Collection<E> fillCollection(
        int size,
        Collection<E> collection,
        Supplier<E> getElement)
    {
        System.out.printf("Filling %s (%s)%n", collection.getClass().getSimpleName(), size);
        for (int i = 0; i < size; i++) {
            E e = getElement.get();
            collection.add(e);
        }
        System.out.printf("%s is full, size: %s%n", collection.getClass().getSimpleName(), collection.size());

        return collection;
    }
}
