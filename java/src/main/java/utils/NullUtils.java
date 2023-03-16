package utils;

import java.util.function.Function;

public final class NullUtils {

    public static <E, F> F get(E nullable, Function<E, F> fieldGetter) {
        if (nullable == null) return null;
        return fieldGetter.apply(nullable);
    }

    public static <E> E get(E defaultValue, E nullable) {
        if (nullable == null) return defaultValue;
        return nullable;
    }

    public static <E, F> F get(F defaultValue, E nullable, Function<E, F> fieldGetter) {
        if (nullable == null) return defaultValue;
        F applied = fieldGetter.apply(nullable);
        return applied == null ? defaultValue : applied;
    }
}
