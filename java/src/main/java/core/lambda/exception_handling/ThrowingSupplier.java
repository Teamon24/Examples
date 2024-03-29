package core.lambda.exception_handling;

import utils.Invoker;

import java.util.Optional;

import static core.lambda.exception_handling.ThrowingLambdasEssential.rethrowIfAnotherIsCaught;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> {

    /**
     * <p>Метод позволяет обрабатывать исключения, которые могут возникнуть при выполнении лямбды,
     * в теле которой вызываются методы с объявленными исключениями.
     *
     * <p>Описание:
     * <p>- если выброса исключения не произошло, - метод возвращает результат выполнения лямбды.
     * <p>- если произошел выброс ожидаемого исключения, то будет возврат пустого Optional или проброс пойманного исключения (если установлен соответствующий флаг).
     * <p>- если произошел выброс исключения другого типа, то будет проброс.
     * <p>- finally-блок выполняется в любом случае.
     *
     * @param supplier      лямбда, которая может выбросить любое исключние.
     * @param expectedClass класс исключения, выброс которого ожидается при выполенении лямбды.
     * @param rethrows      пробрасывать ли ожидаемое исключние дальше в случае его поимки.
     * @param finallyBlock  логика finally-блока.
     * @param <R>           тип возвращаемого значения лямбды.
     * @param <Ex>          тип ожидаемого исключения.
     * @return результат выполнения лямбды.
     */
    static <R, Ex extends Throwable> R rethrow(
        ThrowingSupplier<R, Ex> supplier,
        Class<Ex> expectedClass,
        boolean rethrows,
        Invoker finallyBlock
    ) {
        try {
            return supplier.get();
        } catch (Throwable actualException) {
            rethrowIfAnotherIsCaught(expectedClass, actualException, rethrows);
        } finally {
            finallyBlock.invoke();
        }
        return null;
    }

    static <T, E extends Throwable> T rethrow(
        Class<E> expectedExceptionClass,
        ThrowingSupplier<T, E> tryBlock
    ) {
        boolean rethrows = true;
        Invoker emptyCatchBlock = () -> {};
        return ThrowingSupplier.rethrow(tryBlock, expectedExceptionClass, rethrows, emptyCatchBlock);
    }

    static <T, E extends Throwable> Optional<T> get(
        ThrowingSupplier<T, E> tryBlock,
        Class<E> expectedExceptionClass
    ) {
        boolean rethrows = false;
        Invoker emptyCatchBlock = () -> {};
        return Optional.ofNullable(
            ThrowingSupplier.rethrow(
                tryBlock, expectedExceptionClass, rethrows, emptyCatchBlock));
    }

    T get() throws E;
}