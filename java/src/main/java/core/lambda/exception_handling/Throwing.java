package core.lambda.exception_handling;

import utils.Voider;

import static core.lambda.exception_handling.ThrowingLambdasEssential.rethrowIfAnotherIsCaught;

@FunctionalInterface
public interface Throwing<E extends Throwable> {

    static <E extends Throwable> void rethrow(
        Throwing<E> tryBlock,
        Class<E> expectedClass,
        boolean rethrows,
        Voider catchBlock
    ) {
        try {
            tryBlock.invoke();
        } catch (Throwable actualException) {
            catchBlock.invoke();
            rethrowIfAnotherIsCaught(expectedClass, actualException, rethrows);
        }
    }

    static <E extends Throwable> void rethrow(
        Class<E> expectedClass,
        Throwing<E> tryBlock,
        Voider catchBlock
    ) {
        boolean rethrows = true;
        Throwing.rethrow(tryBlock, expectedClass, rethrows, catchBlock);
    }

    static <E extends Throwable> void rethrow(
        Class<E> expectedExceptionClass, Throwing<E> tryBlock
    ) {
        boolean rethrows = true;
        Voider catchBlock = () -> {};
        Throwing.rethrow(tryBlock, expectedExceptionClass, rethrows, catchBlock);
    }

    void invoke() throws E;
}