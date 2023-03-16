package core.lambda.exception_handling;

import utils.Invoker;

import static core.lambda.exception_handling.ThrowingLambdasEssential.rethrowIfAnotherIsCaught;

@FunctionalInterface
public interface ThrowingInvoker<E extends Throwable> {

    static <E extends Throwable> void rethrow(
        Class<E> expectedClass,
        ThrowingInvoker<E> tryBlock,
        Invoker finallyBlock
    ) {
        boolean rethrows = true;
        ThrowingInvoker.rethrow(tryBlock, expectedClass, rethrows, finallyBlock);
    }

    static <E extends Throwable> void rethrow(
        Class<E> expectedExceptionClass,
        ThrowingInvoker<E> tryBlock
    ) {
        boolean rethrows = true;
        Invoker finallyBlock = () -> {};
        ThrowingInvoker.rethrow(tryBlock, expectedExceptionClass, rethrows, finallyBlock);
    }

    static <E extends Throwable> void rethrow(
        ThrowingInvoker<E> tryBlock,
        Class<E> expectedClass,
        boolean rethrows,
        Invoker finallyBlock
    ) {
        try {
            tryBlock.invoke();
        } catch (Throwable actualException) {
            rethrowIfAnotherIsCaught(expectedClass, actualException, rethrows);
        } finally {
            finallyBlock.invoke();
        }
    }

    void invoke() throws E;
}