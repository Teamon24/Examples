package core.base.comparing;

import java.util.Collection;
import java.util.Comparator;

public final class Comparators {

    public static final <T> Comparator<T> comparator(
        Collection<Comparator<T>> comparators
    ) {
        return (T o1, T o2) -> {
            int result = 0;
            for (Comparator<T> comparator : comparators) {
                int compared = comparator.compare(o1, o2);
                if (compared != 0) {
                    return compared;
                }
            }
            return result;
        };
    }
}
