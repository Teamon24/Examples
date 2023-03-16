package core.lambda.exception_handling;

import utils.Invoker;

import java.util.function.Consumer;

import static core.lambda.exception_handling.ThrowingLambdasEssential.rethrowIfAnotherIsCaught;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    void accept(T t) throws E;

    static <T, E extends Throwable> Consumer<T> wrap(
        ThrowingConsumer<T, E> tryBlock,
        Class<E> expectedClass)
    {
        return ThrowingConsumer.wrap(tryBlock, expectedClass, true, () -> {});
    }

    static <T, E extends Throwable> Consumer<T> wrap(
        ThrowingConsumer<T, E> tryBlock,
        Class<E> expectedClass,
        boolean rethrows,
        Invoker finallyBlock)
    {
        return (arg) -> {
            try {
                tryBlock.accept(arg);
            } catch (Throwable actualException) {
                rethrowIfAnotherIsCaught(expectedClass, actualException, rethrows);
            } finally {
                finallyBlock.invoke();
            }
        };
    }

    static <T, E extends Throwable> Consumer<T> wrap(
        ThrowingConsumer<T, E> tryBlock,
        Class<E> expectedClass,
        Invoker finallyBlock)
    {
        return ThrowingConsumer.wrap(tryBlock, expectedClass, true, finallyBlock);
    }
}



