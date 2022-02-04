package utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StreamUtils {
    public static <T, E> Set<T> getUniques(final Collection<E> collection,
                                           final Function<E, T> uniqueFieldExtractor)
    {
        Set<T> collectionTypes = collection
            .stream()
            .map(uniqueFieldExtractor)
            .collect(Collectors.toSet());
        return collectionTypes;
    }

    public static <T> Stream<? extends T> flat(List<? extends T> ... belows) {
        return Stream.of(belows).flatMap(Collection::stream);
    }
}
