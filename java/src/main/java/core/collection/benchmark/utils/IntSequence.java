package core.collection.benchmark.utils;

import java.util.function.Function;

public class IntSequence extends Sequence<Integer> {
    protected IntSequence(Integer first) {
        super(first);
    }

    public static IntSequence first(final Integer first) {
        return new IntSequence(first);
    }

    @Override
    public IntSequence next(Function<Integer, Integer> generateNext) {
        return (IntSequence) super.next(generateNext);
    }

    public Sequence<Integer> fromLast(Integer period, Integer limit) {
        Integer current = super.current;
        if (limit - current % period < 1) {
            String template = "There should be at least one repeat: period - %s > difference (limit - from) - %s";
            String message = String.format(template, period, limit - current);
            throw new RuntimeException(message);
        }

        Sequence<Integer> sequence = Sequence.first(current);
        int periods = (limit - current) / period;
        final int[] counter = {0};
        sequence.next((ignored) -> {
            if (counter[0] > periods) {
                counter[0] = 0;
            }

            int value = period * counter[0];
            counter[0]++;
            return value;
        });
        return sequence;
    }

    public static IntSequence create() {
        Integer first = 0;
        return IntSequence.first(first).next((it -> it = it + 1));
    }
}
