package core.collection.benchmark.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StreamUtils {
    public static Stream<?> concat(final Stream<?>... testInfosBatch) {
        if (testInfosBatch.length == 0) {
            throw new RuntimeException("Amount of streams should not be zero.");
        }
        Stream<?> currStream = Arrays.stream(testInfosBatch).findFirst().get();
        Stream<?> stream = null;
        for (int i = 1; i < testInfosBatch.length; i++) {
            stream = Stream.concat(currStream, testInfosBatch[i]);
            currStream = stream;
        }
        assert stream != null;
        return stream;
    }

    public static <T, E> Set<T> getUniques(final List<E> list,
                                           final Function<E, T> uniqueFieldExtractor)
    {
        Set<T> collectionTypes = list
            .stream()
            .map(uniqueFieldExtractor)
            .collect(Collectors.toSet());
        return collectionTypes;
    }
}
