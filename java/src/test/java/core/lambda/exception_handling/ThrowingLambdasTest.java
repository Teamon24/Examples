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

import static core.lambda.exception_handling.Throwing.*;
import static core.lambda.exception_handling.ThrowingConsumer.*;
import static core.lambda.exception_handling.ThrowingObject.createEx;
/**
 *
 */
class ThrowingLambdasTest {

    private static final Voider EMPTY_CATCH_BLOCK = () -> {};

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
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
            rethrow(object::method, expectedExceptionClass, rethrowsExpected, EMPTY_CATCH_BLOCK);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method);
    }

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
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
            Consumer consumer = wrap((i) -> object.method(),
                expectedExceptionClass,
                rethrowsExpected,
                EMPTY_CATCH_BLOCK);

            consumer.accept(null);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method);
    }

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
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
            rethrow(object::method, expectedExceptionClass, rethrowsExpected, EMPTY_CATCH_BLOCK);
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
                String template = "Test: actual is %s, but expected is %s";
                System.out.printf(template + "%n",
                    cause.getClass().getSimpleName(), expectedExceptionClass.getSimpleName()
                );
                Assertions.assertNotEquals(expectedExceptionClass, cause.getClass());
                Assertions.assertEquals(unexpectedException.getClass(), cause.getClass());
            } else {
                if (rethrowsExpected) {
                    Assertions.assertEquals(expectedExceptionClass, cause.getClass());
                    String template = "Test: rethrown exception is '%s";
                    System.out.printf((template) + "%n", expectedExceptionClass.getSimpleName());
                }
            }
        }
    }

    private static Stream<Arguments> providerForThrowingTest() {
        List rethrowsExpected = List.of(true, false);
        List noExceptionThrowing = List.of(true, false);
        List expectedExceptions = List.of(SQLException.class, IOException.class, IllegalArgumentException.class);
        List unexpectedExceptions = CollectionUtils.arrayList(NumberFormatException.class, ArithmeticException.class, null);

        List<List> lists = Cartesian.product(rethrowsExpected, noExceptionThrowing, expectedExceptions, unexpectedExceptions);
        return lists.stream().map(list -> Arguments.arguments(list.toArray()));
    }
}