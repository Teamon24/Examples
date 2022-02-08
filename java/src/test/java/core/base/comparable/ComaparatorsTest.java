package core.base.comparable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


class ComaparatorsTest {

    @Test
    void comparators() {
        int uniqueAmount = 15;
        int repeated = 5;

        List<A> as = createList(uniqueAmount, repeated);
        Collections.shuffle(as);

        List<A> objects = new ArrayList<>(as);
        objects.sort(A.COMPARATOR);
        Assertions.assertEquals(objects.size(), uniqueAmount + repeated);

        TreeSet<A> treeSet = new TreeSet<>(as);
        Assertions.assertEquals(treeSet.size(), uniqueAmount);

    }

    static List<A> createList(int uniqueAmount, int repeated) {
        List<Object[]> values = values(uniqueAmount);
        List<A> collect = values.stream()
            .map(it ->
                A.of(
                    (Integer) it[0],
                    it[1].toString(),
                    C.of(
                        (Integer) it[2],
                        it[3].toString())))
            .collect(Collectors.toList());

        A first = collect.get(0);

        for (int i = 0; i < repeated; i++) {
            collect.add(first.clone());
        }

        return collect;
    }

    public static final List<Object[]> values(Integer amount) {
        List<Object[]> result = new ArrayList<>();
        Object[] randomValues = RandomUtils.random(1, 4, 4);
        for (int i = 0; i < amount; i++) {
            while (contains(result, randomValues)) {
                randomValues = RandomUtils.random(1, 4, 4);
            }
            result.add(randomValues);
        }

        return result;
    }

    private static boolean contains(List<Object[]> result, Object[] value) {
        for (Object[] objects : result) {
            if (equals(objects, value)) {
                return true;
            }
        }
        return false;
    }

    private static boolean equals(Object[] result, Object[] value) {
        if (result.length != value.length) throw new IllegalArgumentException("lengths should be equal");
        for (int i = 0; i < result.length; i++) {
            if (result[i] != value[i]) return false;
        }
        return true;
    }
}