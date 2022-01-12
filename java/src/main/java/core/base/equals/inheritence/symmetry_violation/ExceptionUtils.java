package core.base.equals.inheritence.symmetry_violation;

public class ExceptionUtils {
    public static <T> RuntimeException exceptionIfNoInstanceOfCase(T thiz, Object o) {
        String template = "There is no instance-of-check for case: %s#equals(%s)";
        String message = String.format(template, thiz.getClass().getSimpleName(), o.getClass().getSimpleName());
        return new RuntimeException(message);
    }
}
