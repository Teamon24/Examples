package core.base.equals_hashcode.equals.inheritence;

import static java.lang.String.*;
import static utils.ClassUtils.simpleName;

public class ExampleUtils {

    public static RuntimeException exceptionIfNoInstanceOf(Object o1, Object o2) {
        String template = "There is no instance-of check for class '%2$s' in %1$s#equals(%2$s)";
        String message = format(template, simpleName(o1), simpleName(o2));
        return new RuntimeException(message);
    }
}
