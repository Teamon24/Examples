package core.lambda.exception_handling;

import static core.lambda.exception_handling.ThrowingLambdasEssential.throwAnotherIfWasCaught;

@FunctionalInterface
public interface Throwing<E extends Throwable> {
    void rethrow() throws E;

    static <E extends Throwable> void rethrow(
        Throwing<E> tryBlock,
        Class<E> expectedClass,
        boolean rethrows,
        Voider catchBlock)
    {
        try {
            tryBlock.rethrow();
        } catch (Throwable actualException) {
            catchBlock.invoke();
            throwAnotherIfWasCaught(expectedClass, actualException, rethrows);
        }
    }

    static <E extends Throwable> void rethrow(
        Class<E> expectedClass, Throwing<E> tryBlock,
        Voider catchBlock)
    {
        Throwing.rethrow(tryBlock, expectedClass, true, catchBlock);
    }

    static <E extends Throwable> void rethrow(
        Class<E> expectedExceptionClass, Throwing<E> tryBlock)
    {
        Throwing.rethrow(tryBlock, expectedExceptionClass, true, () -> {});
    }
}