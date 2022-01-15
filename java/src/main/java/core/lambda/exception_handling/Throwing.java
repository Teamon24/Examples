package core.lambda.exception_handling;

import static core.lambda.exception_handling.ThrowingLambdasEssential.throwAnotherIfWasCaught;

@FunctionalInterface
public interface Throwing<E extends Throwable> {
    void invoke() throws E;

    static <E extends Throwable> void tryCatch(
        Throwing<E> tryBlock,
        Class<E> expectedClass,
        boolean rethrows,
        Voider catchBlock)
    {
        try {
            tryBlock.invoke();
        } catch (Throwable actualException) {
            throwAnotherIfWasCaught(expectedClass, actualException, rethrows);
            catchBlock.invoke();
        }
    }

    static <E extends Throwable> void tryCatch(
        Throwing<E> tryBlock,
        Class<E> expectedClass,
        Voider catchBlock)
    {
        Throwing.tryCatch(tryBlock, expectedClass, true, catchBlock);
    }

    static <E extends Throwable> void tryCatch(
        Throwing<E> tryBlock,
        Class<E> expectedExceptionClass)
    {
        Throwing.tryCatch(tryBlock, expectedExceptionClass, true, () -> {});
    }
}