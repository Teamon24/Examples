package utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class StreamUtils {

    public static <T> Stream<? extends T> flat(List<? extends T> ... belows) {
        return Stream.of(belows).flatMap(Collection::stream);
    }

}
