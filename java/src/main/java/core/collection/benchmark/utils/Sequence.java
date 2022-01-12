package core.collection.benchmark.utils;

import java.util.function.Function;

public class Sequence<In> {

    protected Function<In, In> generateNext;
    protected In current;
    protected In first;
    protected Integer counter = 0;

    protected Sequence(final In first) {
        this.first = first;
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
        if (counter == 0) {
            counter++;
            return this.first;
        }

        if (generateNext == null) throw new RuntimeException("sequence has no logic of next element generation");

        In next = generateNext.apply(current);
        this.current = next;

        counter++;
        return next;
    }

    public static Sequence<Integer> intSequence() {
        return Sequence.first(0).init((it -> it = it + 1));
    }
}
