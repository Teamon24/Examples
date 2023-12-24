package core.base.comparing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static core.base.comparing.ExampleUtils.values;

public class Demo {
    public static void main(String[] args) {
        int uniqueAmount = 30;
        int repeated = 5;

        List<A> as = createList(uniqueAmount, repeated);
        sout("initial list", () -> {
            Collections.shuffle(as);
            return as;
        });


        sout("sort with comparator", () -> {
            List<A> list = new ArrayList<>(as);
            list.sort(Comparators.comparator(A.comparators));
            return list;
        });

        sout("sort with comparator", () -> new TreeSet<>(as));
    }

    private static void sout(String title, Supplier<Collection<A>> action) {
        System.out.println(title);
        action.get().forEach(System.out::println);
        System.out.println();
    }

    static List<A> createList(int uniqueAmount, int repeated) {
        List<Integer[]> values = values(uniqueAmount);
        List<A> collect = values.stream()
            .map(Demo::createA)
            .collect(Collectors.toList());

        A first = collect.get(0);

        for (int i = 0; i < repeated; i++) {
            collect.add(first.clone());
        }

        return collect;
    }

    private static A createA(Object[] it) {
        return A.of((Integer) it[0], it[1].toString(), C.of((Integer) it[2], it[3].toString()));
    }

}
