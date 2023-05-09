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

import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;

public final class CollectionCreationUtils {

    public static <T> List<T> createList(final Class listClass, final int size, final Supplier<T> supplier) {

        if (listClass.equals(ArrayList.class)) {
            return (ArrayList<T>) fillCollection(size, new ArrayList<>(), supplier);
        }

        if (listClass.equals(LinkedList.class)) {
            LinkedList<T> ts = (LinkedList<T>) fillCollection(size, new LinkedList<>(), supplier);
            //переводим указатель на начало списка
            ts.getFirst();
            return ts;
        }

        if (listClass.equals(TreeList.class)) {
            return (TreeList<T>) fillCollection(size, new TreeList<>(), supplier);
        }

        throw new RuntimeException("There is no list initialization case for class: " + listClass.getSimpleName());
    }

    public static <T extends Comparable<T>> Set<T> createSet(final Class setClass, final int size, final Supplier<T> supplier) {

        if (setClass.equals(HashSet.class)) {
            return (HashSet<T>) fillCollection(size, new HashSet<>(), supplier);
        }

        if (setClass.equals(LinkedHashSet.class)) {
            return (LinkedHashSet<T>) fillCollection(size, new LinkedHashSet<>(), supplier);
        }

        if (setClass.equals(TreeSet.class)) {
            return (TreeSet<T>) fillCollection(size, new TreeSet<>(), supplier);
        }


        throw new RuntimeException("In set initialization method no implementation for class: " + setClass.getSimpleName());
    }

    public static <E> Collection<E> fillCollection(
        int size,
        Collection<E> collection,
        Supplier<E> getElement)
    {
        printfln("Filling %s (%s)", simpleName(collection), size);
        for (int i = 0; i < size; i++) {
            E e = getElement.get();
            collection.add(e);
        }
        printfln("%s is full, size: %s", simpleName(collection), collection.size());

        return collection;
    }
}
