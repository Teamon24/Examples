package core.collection.benchmark.utils;

import java.util.function.Function;

public final class Sequence<In> {

    private Function<In, In> generateNext;
    private In current;

    private Sequence(final In first) {
        this.current = first;
    }

    public static <In> Sequence<In> first(final In first) {
        return new Sequence<>(first);
    }

    public Sequence<In> init(final Function<In, In> generateNext) {
        this.generateNext = generateNext;
        return this;
    }

    public In next() {
        In next = generateNext.apply(current);
        this.current = next;
        return next;
    }
}
