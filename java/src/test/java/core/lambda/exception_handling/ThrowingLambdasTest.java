package core.lambda.exception_handling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Cartesian;
import utils.CollectionUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static core.lambda.exception_handling.Throwing.tryCatch;
import static core.lambda.exception_handling.ThrowingObject.createEx;
/**
 *
 */
class ThrowingLambdasTest {

    private static final Voider EMPTY_CATCH_BLOCK = () -> {};

    @ParameterizedTest
    @MethodSource("provideForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowing(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass)
    {
        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object = new ThrowingObject<>(noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException);

        Function method = (ignored) -> {
            tryCatch(object::method, expectedExceptionClass, rethrowsExpected, EMPTY_CATCH_BLOCK);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method);
    }

    @ParameterizedTest
    @MethodSource("provideForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowingConsumer(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass)
    {

        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object = new ThrowingObject<>(noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException);

        Function method = (ignored) -> {
            Consumer consumer = ThrowingConsumer.wrap((i) -> object.method(),
                expectedExceptionClass,
                rethrowsExpected,
                EMPTY_CATCH_BLOCK);

            consumer.accept(null);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method);
    }

    @ParameterizedTest
    @MethodSource("provideForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowingSupplier(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass)
    {
        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object =
            new ThrowingObject<>(
                noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException
            );

        Function method = (ignored) -> {
            tryCatch(object::method, expectedExceptionClass, rethrowsExpected, EMPTY_CATCH_BLOCK);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method);
    }

    void testTryCatchLogic(
        boolean rethrowsExpected,
        Class expectedExceptionClass,
        Exception unexpectedException,
        Function<?, ?> method)
    {
        Exception actual;
        try {
            method.apply(null);
        } catch (Exception e) {
            actual = e;

            Throwable cause = actual.getCause();
            if (unexpectedException != null) {
                Assertions.assertNotEquals(expectedExceptionClass, cause.getClass());
                Assertions.assertEquals(unexpectedException.getClass(), cause.getClass());
            } else {
                if (rethrowsExpected) {
                    Assertions.assertEquals(expectedExceptionClass, cause.getClass());
                }
            }
        }
    }

    private static Stream<Arguments> provideForThrowingTest() {
        List rethrowsExpected = List.of(true, false);
        List noExceptionThrowing = List.of(true, false);
        List expectedExceptions = List.of(SQLException.class, IOException.class, IllegalArgumentException.class);
        List unexpectedExceptions = CollectionUtils.arrayList(NumberFormatException.class, ArithmeticException.class, null);

        List<List> lists = Cartesian.product(rethrowsExpected, noExceptionThrowing, expectedExceptions, unexpectedExceptions);
        return lists.stream().map(list -> Arguments.arguments(list.toArray()));
    }
}