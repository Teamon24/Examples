package utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public  static <T> ArrayList<T> arrayList(T ... nullables) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < nullables.length; i++) {
            arrayList.add(nullables[i]);
        }

        return arrayList;
    }
}
