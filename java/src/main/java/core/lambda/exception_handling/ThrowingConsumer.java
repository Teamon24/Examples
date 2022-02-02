package core.lambda.exception_handling;

import utils.Voider;

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
        Voider catchBlock)
    {
        return (arg) -> {
            try {
                tryBlock.accept(arg);
            } catch (Throwable actualException) {
                rethrowIfAnotherIsCaught(expectedClass, actualException, rethrows);
                catchBlock.invoke();
            }
        };
    }

    static <T, E extends Throwable> Consumer<T> wrap(
        ThrowingConsumer<T, E> tryBlock,
        Class<E> expectedClass,
        Voider catchBlock)
    {
        return ThrowingConsumer.wrap(tryBlock, expectedClass, true, catchBlock);
    }
}



