package core.base.fnally;

import lombok.AllArgsConstructor;

import java.util.function.Supplier;

import static java.lang.System.out;

@AllArgsConstructor
public class FinallyBlock<T> implements Supplier<T>{
    private Boolean returns;
    private Supplier<T> block;

    public Boolean returns() {
        return returns;
    }

    @Override
    public T get() {
        return this.block.get();
    }

    public static <T> FinallyBlock<T> finallyBlock() {
        return finallyBlock(() -> null, null, false);
    }

    public static <T> FinallyBlock<T> finallyBlock(T returnedValue) {
        return finallyBlock(() -> null, returnedValue, true);
    }

    public static <T> FinallyBlock<T> finallyBlock(Supplier<? extends RuntimeException> exceptionSupplier) {
        return finallyBlock(exceptionSupplier, null, false);
    }

    public static <T> FinallyBlock<T> finallyBlock(
        Supplier<? extends RuntimeException> exceptionSupplier,
        T returnedValue,
        Boolean returns
    ) {
        return new FinallyBlock<>(returns, () -> {
            if (exceptionSupplier.get() != null) {
                out.println("Finally block: throwing an exception\n");
                throw exceptionSupplier.get();
            }
            out.println("Finally block: execution was done\n");
            return returnedValue;
        });
    }
}
