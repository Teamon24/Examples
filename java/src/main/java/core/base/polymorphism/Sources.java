package core.base.polymorphism;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Sources<T> extends ArrayList<T> {
    @Getter
    private final List<Source<? extends T>> sources;

    public Sources(List<Source<? extends T>> sources) {
        this.sources = sources;
    }

    public static <T> Sources<T> of(Source<? extends T>... sources) {
        List<Source<? extends T>> collect = new ArrayList<>(Arrays.asList(sources));
        return new Sources<>(collect);
    }

    public Sources<T> println() {
        this.getSources().sort((o1, o2) -> {
            Class<?> value2Class = o2.value.getClass();
            Class<?> value1Class = o1.value.getClass();

            if (value1Class.equals(value2Class)) {
                return 0;
            } else if (value1Class.isInstance(o2.value)) {
                return -1;
            } else {
                return 1;
            }
        });
        System.out.println();
        this.getSources().forEach(System.out::println);
        return this;
    }
}
