package core.base.equals.symmetry_violation;

public class ExceptionUtils {
    public static <T> RuntimeException exceptionIfNoInstanceOfCheck(T t, Object o) {
        String template = "There is no instance-of-check for case: %s#equals(%s)";
        String message = String.format(template, t.getClass().getSimpleName(), o.getClass().getSimpleName());
        return new RuntimeException(message);
    }
}
