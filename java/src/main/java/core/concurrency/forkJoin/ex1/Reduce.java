package core.concurrency.forkJoin.ex1;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;

public class Reduce<T> extends RecursiveTask<T> {

    private final List<T> elements;
    private final BiFunction<T, T, T> reducer;

    public Reduce(final List<T> elements,
                  final BiFunction<T, T, T> reducer)
    {
        this.elements = elements;
        this.reducer = reducer;
    }

    @Override
    protected T compute() {
        int listSize = this.elements.size();
        if (listSize == 0) throw new RuntimeException("Elements amount should not be zero");
        if (listSize == 1) return this.elements.get(0);
        if (listSize == 2) {
            return reducer.apply(this.elements.get(0), this.elements.get(0));
        }

        int middle = listSize / 2;

        final Reduce<T> reduce1 = new Reduce<>(this.elements.subList(0, middle), reducer);
        final Reduce<T> reduce2 = new Reduce<>(this.elements.subList(middle, listSize), reducer);

        reduce1.fork();
        reduce2.fork();

        return reducer.apply(reduce1.join(), reduce2.join());
    }
}
