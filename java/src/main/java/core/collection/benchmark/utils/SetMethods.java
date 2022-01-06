package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

public final class SetMethods {
    public static <T> BiConsumer<Collection<T>, T> remove() {
        return Collection::remove;
    }

    public static <T> BiConsumer<Collection<T>, T> addElement() {
        return Collection::add;
    }
}
