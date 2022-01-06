package core.collection.benchmark.utils;

import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

public final class CollectionCreationUtils {

    public static <T> List<T> list(final Class listClass, final int size, final Supplier<T> supplier) {

        if (listClass.equals(LinkedList.class)) {
            return (LinkedList<T>) fillCollection(size, new LinkedList<>(), supplier);
        }

        if (listClass.equals(ArrayList.class)) {
            return (ArrayList<T>) fillCollection(size, new ArrayList<>(), supplier);
        }

        if (listClass.equals(TreeList.class)) {
            return (TreeList<T>) fillCollection(size, new TreeList<>(), supplier);
        }

        throw new RuntimeException("In list initialization method no implementation for class: " + listClass.getSimpleName());
    }

    public static <T> Set<T> set(final Class setClass, final int size, final Supplier<T> supplier) {

        if (setClass.equals(HashSet.class)) {
            return (HashSet<T>) fillCollection(size, new HashSet<>(), supplier);
        }

        if (setClass.equals(TreeSet.class)) {
            return (TreeSet<T>) fillCollection(size, new TreeSet<>(), supplier);
        }

        if (setClass.equals(LinkedHashSet.class)) {
            return (LinkedHashSet<T>) fillCollection(size, new LinkedHashSet<>(), supplier);
        }

        throw new RuntimeException("In set initialization method no implementation for class: " + setClass.getSimpleName());
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
