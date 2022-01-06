package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public final class MethodSuppliers {
    public static <E> BiConsumer<Collection<E>, E> getRemoveElement(final Collection<E> collection) {
        if (collection instanceof List) {
            return ListMethods.removeElement();
        }

        if (collection instanceof Set) {
            return SetMethods.remove();
        }

        throw new RuntimeException("Unexpected value for removing element: %s" + collection.getClass());
    }

    public static <E> BiConsumer<Collection<E>, Integer> getRemoveByIndex(final Collection<E> collection) {
        if (collection instanceof List) {
            return ListMethods.removeByIndex();
        }

        throw new RuntimeException("Unexpected value for removing by index: %s" + collection.getClass());
    }

    public static <E> BiConsumer<Collection<E>, E> getAddElement(final Collection<E> collection) {
        if (collection instanceof List) {
            return Collection::add;
        }

        if (collection instanceof Set) {
            return Collection::add;
        }

        throw new RuntimeException("Unexpected value for adding element: %s" + collection.getClass());
    }
}
