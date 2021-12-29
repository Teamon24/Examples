package benchmark.collection.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public final class ListsSupplier {

    public static <E> Supplier<Collection<E>> sameArrayList(ArrayList<E> arrayList) {
        return () -> arrayList;
    }

    public static <E> Supplier<Collection<E>> newArrayList(Collection<E> collection) {
        return () -> new ArrayList<>(collection);
    }

    public static <E> Supplier<Collection<E>> sameLinkedList(LinkedList<E> linkedList) {
        return () -> {
            linkedList.get(0);//для того чтобы переместить указатель на первый элемент
            return linkedList;
        };
    }

    public static <E> Supplier<Collection<E>> newLinkedList(Collection<E> linkedList) {
        return () -> {
            List<E> newList = new LinkedList<>(linkedList);
            newList.get(0);
            return newList;
        };
    }
}
