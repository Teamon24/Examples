package utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class JoinUtils {
    public static <T> String join(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) return "";
        return StringUtils.joinWith(", ", collection);
    }
}
