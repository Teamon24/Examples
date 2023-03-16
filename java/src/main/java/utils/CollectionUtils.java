package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectionUtils {
    public  static <T> ArrayList<T> arrayList(T ... nullables) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < nullables.length; i++) {
            arrayList.add(nullables[i]);
        }

        return arrayList;
    }

    public static <T> String join(Collection<T> collection) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(collection)) return "";
        return StringUtils.joinWith(", ", collection);
    }

    public static <T> List<T> getByIndexes(List<T> elements, List<Integer> indexes) {
        List<T> filtered = new ArrayList<>(indexes.size());
        for (int i = 0; i < elements.size(); i++) {
            if (indexes.contains(i)) {
                filtered.add(elements.get(i));
            }
        }
        return filtered;
    }

    public static <T> List<T> toList(List<? extends T> ... belows) {
        return Stream.of(belows).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static <T, E> Set<T> getUniques(final Collection<E> collection,
                                           final Function<E, T> uniqueFieldExtractor)
    {
        Set<T> collectionTypes = collection
            .stream()
            .map(uniqueFieldExtractor)
            .collect(Collectors.toSet());
        return collectionTypes;
    }
}
