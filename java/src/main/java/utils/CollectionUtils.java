package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CollectionUtils {
    public  static <T> ArrayList<T> arrayList(T ... nullables) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < nullables.length; i++) {
            arrayList.add(nullables[i]);
        }

        return arrayList;
    }

    public static <T> String join(Collection<T> collection) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(collection)) return "";
        return StringUtils.joinWith(", ", collection);
    }

    public static <T> List<T> getByIndexes(List<T> courses, List<Integer> indexes) {
        List<T> filtered = new ArrayList<>(indexes.size());
        for (int i = 0; i < courses.size(); i++) {
            if (indexes.contains(i)) {
                filtered.add(courses.get(i));
            }
        }
        return filtered;
    }
}
