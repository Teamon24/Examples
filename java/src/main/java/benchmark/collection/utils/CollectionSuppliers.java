package benchmark.collection.utils;

import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.function.Supplier;

public final class CollectionSuppliers {
    public static <E> Supplier<Collection<E>> getNewCollectionSupplier(Collection<E> collection) {
        Class<? extends Collection> collectionClass = collection.getClass();
        if (collectionClass.equals(LinkedList.class)) {
            return () -> {
                LinkedList<E> newList = new LinkedList<>(collection);
                newList.get(0);
                return newList;
            };
        }

        if (collectionClass.equals(ArrayList.class)) {
            return () -> new ArrayList<>(collection);
        }

        if (collectionClass.equals(TreeList.class)) {
            return () -> new TreeList<>(collection);
        }

        if (collectionClass.equals(HashSet.class)) {
            return () -> new HashSet<>(collection);
        }

        if (collectionClass.equals(TreeSet.class)) {
            return () -> new TreeSet<>(collection);
        }

        if (collectionClass.equals(LinkedHashSet.class)) {
            return () -> new LinkedHashSet<>(collection);
        }

        throw new RuntimeException("Unexpected class: " + collectionClass.getSimpleName());
    }

    public static <E> Supplier<Collection<E>> getSameCollectionSupplier(Collection<E> collection) {
        return () -> collection;
    }
}
