package core.collection.benchmark.utils;

import java.util.function.Function;
import java.util.function.Supplier;

public class Sequence<In> {

    protected Supplier<In> firstSupplier;
    protected Function<In, In> generateNext;
    protected In current;
    protected In first;
    protected Integer stepNumber = 0;

    protected Sequence(final Supplier<In> firstSupplier) {
        this.firstSupplier = firstSupplier;
    }

    protected Sequence(final In first) {
        this.first = first;
        this.current = first;
    }

    public static <In> Sequence<In> first(final Supplier<In> firstSupplier) {
        return new Sequence<>(firstSupplier);
    }

    public static <In> Sequence<In> first(final In first) {
        return new Sequence<>(first);
    }

    public Sequence<In> next(final Function<In, In> generateNext) {
        this.generateNext = generateNext;
        return this;
    }

    public In next() {
        if (this.generateNext == null) throw new RuntimeException("Sequence has no logic of next element generation.");

        if (this.stepNumber == 0) {
            this.stepNumber++;
            if (this.first == null) {
                if (this.firstSupplier == null) {
                    throw new RuntimeException("Sequence has no first element");
                } else {
                    return this.firstSupplier.get();
                }
            } else {
                return this.first;
            }
        }

        In next = this.generateNext.apply(this.current);
        this.current = next;

        this.stepNumber++;
        return next;
    }

}
