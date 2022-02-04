package core.collection.benchmark.utils;

import utils.RandomUtils;

import java.util.Collection;
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

    public static <In> Sequence<In> randomFrom(Collection<In> collection) {
        return Sequence.first(RandomUtils.randomFrom(collection)).init((it -> RandomUtils.randomFrom(collection)));
    }

    public Sequence<In> init(final Function<In, In> generateNext) {
        this.generateNext = generateNext;
        return this;
    }

    public In next() {
        if (generateNext == null) throw new RuntimeException("Sequence has no logic of next element generation.");

        if (this.counter == 0) {
            this.counter++;
            if (this.first == null) {
                if (this.current == null) throw new RuntimeException("Sequence has no first element");
                return generateNext.apply(current);
            } else {
                return this.first;
            }
        }

        In next = generateNext.apply(current);
        this.current = next;

        this.counter++;
        return next;
    }

}
