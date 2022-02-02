package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import static java.lang.String.*;
import static utils.ClassUtils.simpleName;

public class ExampleUtils {
    public static <T> RuntimeException exceptionIfNoInstanceOfCase(T thiz, Object o) {
        String template = "There is no instance-of-check for case: %s#equals(%s)";
        String message = format(template, simpleName(thiz), simpleName(o));
        return new RuntimeException(message);
    }
}
