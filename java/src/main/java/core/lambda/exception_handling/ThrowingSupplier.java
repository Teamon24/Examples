package core.lambda.exception_handling;

import static core.lambda.exception_handling.ThrowingLambdasEssential.throwAnotherIfWasCaught;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> {
    T get() throws E;

    /**
     * <p>Метод позволяет обрабатывать исключения, которые могут возникнуть при выполнении лямбды,
     * в теле которой вызываются методы с объявленными исключениями.
     *
     * <p>Описание:
     * <p>- если выброса исключения не произошло, - метод возвращает результат выполнения лямбды.
     * <p>- если произошел выброс ожидаемого исключения, то будет возврат пустого Optional или проброс (если установлен соответствующий флаг).
     * <p>- если произошел выброс исключения другого типа, то будет проброс.
     *
     * @param supplier лямбда, которая может выбросить любое исключние.
     * @param expectedClass класс исключения, выброс которого ожидается при выполенении лямбды.
     * @param rethrows пробрасывать ли ожидаемое исключние дальше в случае его поимки.
     * @param <R> тип возвращаемого значения лямбды.
     * @param <Ex> тип ожидаемого исключения.
     * @return результат выполнения лямбды.
     */
    static <R, Ex extends Throwable> R tryCatch(
        ThrowingSupplier<R, Ex> supplier,
        Class<Ex> expectedClass,
        boolean rethrows,
        Voider catchBlock)
    {
        try {
            return supplier.get();
        } catch (Throwable actualException) {
            throwAnotherIfWasCaught(expectedClass, actualException, rethrows);
            catchBlock.invoke();
        }
        return null;
    }

    static <T, E extends Throwable> T tryCatch(
        ThrowingSupplier<T, E> tryBlock,
        Class<E> expectedExceptionClass)
    {
        return ThrowingSupplier.tryCatch(tryBlock, expectedExceptionClass, true, () -> {});
    }
}