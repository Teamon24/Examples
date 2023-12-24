package core.base.equals_hashcode;

public final class ExampleUtils {
    public static <T> boolean haveDifferentClass(T t1, T t2) {
        return !haveSameClass(t1, t2, t1.getClass());
    }

    public static <T, E> boolean haveSameClass(T t1, T t2, E expectedClass) {
        return t1.getClass().equals(t2.getClass());
    }

    public static <T> boolean haveSameClass(T t1, T t2) {
        return haveSameClass(t1, t2, t1.getClass());
    }
}
