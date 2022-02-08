package utils;

import java.util.function.Function;

public class NullUtils {
    public static <E, F> String get(E nullable, Function<E, F> fieldGetter) {
        if (nullable == null) return "null";
        F applied = fieldGetter.apply(nullable);
        return applied == null ? "null" : applied.toString();
    }

    public static <E, F> F get(F defaultValue, E nullable, Function<E, F> fieldGetter) {
        if (nullable == null) return defaultValue;
        F applied = fieldGetter.apply(nullable);
        return applied == null ? defaultValue : applied;
    }
}
