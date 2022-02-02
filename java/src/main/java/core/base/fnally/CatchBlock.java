package core.base.fnally;

import utils.PrintUtils;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.ClassUtils.getSimpleName;
import static utils.PrintUtils.printfln;

public interface CatchBlock<T> extends Function<Exception, T> {

    static <T> CatchBlock<T> catchBlock(T defaultValue) {
        return catchBlock(() -> null, defaultValue);
    }

    static <T> CatchBlock<T> catchBlock(Supplier<? extends RuntimeException> exceptionSupplier) {
        return catchBlock(exceptionSupplier, null);
    }

    static <T> CatchBlock<T> catchBlock(
        Supplier<? extends RuntimeException> exceptionSupplier,
        T defaultValue
    ) {
        return (exception) -> {
            String message = exception.getMessage();
            String simpleName = getSimpleName(exception);
            printfln("Catch block: exception handled - %s (%s)\n", simpleName, message);
            if (exceptionSupplier.get() != null) {
                PrintUtils.println("Catch block: throwing an exception");
                throw exceptionSupplier.get();
            }
            return defaultValue;
        };
    }
}
