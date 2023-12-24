package core.base.comparing;

import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ExampleUtils {

    static List<Integer[]> values(Integer size) {
        List<Integer[]> result = new ArrayList<>();
        Supplier<Integer> randomInt = () -> RandomUtils.random(1, 4);

        Integer[] randomValues = RandomUtils.array(Integer.class, 4, randomInt);

        for (int i = 0; i < size; i++) {
            while (contains(result, randomValues)) {
                randomValues = RandomUtils.array(Integer.class, 4, randomInt);
            }
            result.add(randomValues);
        }

        return result;
    }

    static boolean contains(List<Integer[]> result, Integer[] value) {
        for (Object[] objects : result) {
            if (equals(objects, value)) {
                return true;
            }
        }
        return false;
    }

    static boolean equals(Object[] result, Object[] value) {
        if (result.length != value.length) throw new IllegalArgumentException("lengths should be equal");
        for (int i = 0; i < result.length; i++) {
            if (result[i] != value[i]) return false;
        }
        return true;
    }
}
