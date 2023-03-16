package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ClassUtils {
    public static String simpleName(Object o) {
        return o.getClass().getSimpleName();
    }

    public static <T> String joinSimpleNames(Class<? extends T>... classes) {
        return StringUtils.joinWith(", ",
            Arrays
                .stream(classes)
                .map(Class::getSimpleName)
                .collect(Collectors.toList())
        );
    }
    public static <T> String joinSimpleNames(List<Class<? extends T>> classes) {
        List<String> simpleNames = classes.stream().map(Class::getSimpleName).collect(Collectors.toList());
        return StringUtils.joinWith(", ", simpleNames);
    }
}
