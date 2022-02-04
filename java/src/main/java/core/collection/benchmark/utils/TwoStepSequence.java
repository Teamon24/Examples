package core.collection.benchmark.utils;

import java.util.function.Function;

public final class TwoStepSequence<In> extends Sequence<In> {

    private Function<In, In> generateSecond;

    public TwoStepSequence(In first) {
        super(first);
        super.counter = 0;
    }

    public static <In> TwoStepSequence<In> first(final In first) {
        return new TwoStepSequence<>(first);
    }

    public TwoStepSequence<In> init(final Function<In, In> firstStep,
                                    final Function<In, In> secondStep)
    {
        super.generateNext = firstStep;
        this.generateSecond = secondStep;
        return this;
    }

    public TwoStepSequence<In> init(final Function<In, In> secondStep) {
        return this.init(Function.identity(), secondStep);
    }

    public In next() {
        throwNoGenerator(super.generateNext, "sequence has no logic of first step generation");
        throwNoGenerator(this.generateSecond, "sequence has no logic of second step generation");
        if (super.counter == 0) {
            super.counter = 2;
            return super.first;
        }

        if (super.counter == 1) {
            super.current = super.generateNext.apply(super.current);
            super.counter = 2;
        } else {
            super.current = this.generateSecond.apply(super.current);
            super.counter = 1;
        }
        return super.current;
    }

    private void throwNoGenerator(Function<In, In> generateSecond, String message) {
        if (generateSecond == null) throw new RuntimeException(message);
    }

    public TwoStepSequence<In> firstStep() {
        super.counter = 1;
        return this;
    }

    public In get(final int steps) {
        if (steps == 0) return super.current;
        final int counter = super.counter;
        final In current = super.current;
        In result = null;
        for (int i = 0; i < steps; i++) {
            result = this.next();
        }

        super.counter = counter;
        super.current = current;

        return result;
    }
}
