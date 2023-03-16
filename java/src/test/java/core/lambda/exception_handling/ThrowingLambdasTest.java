package core.lambda.exception_handling;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.NullableCartesianProduct;
import utils.CollectionUtils;
import utils.Invoker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static core.lambda.exception_handling.ThrowingConsumer.wrap;
import static core.lambda.exception_handling.ThrowingInvoker.rethrow;
import static core.lambda.exception_handling.ThrowingObject.createEx;
import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;

class ThrowingLambdasTest {

    public static final String FINALLY = "FINALLY";

    private static final Invoker EMPTY_FINALLY_BLOCK = () -> {};

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowing(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass,
        Pair<Invoker, List<String>> finallyBlockPair)
    {
        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object = new ThrowingObject<>(noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException);

        Invoker invoker = finallyBlockPair.getLeft();
        Function method = (ignored) -> {
            rethrow(object::method, expectedExceptionClass, rethrowsExpected, invoker);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method, finallyBlockPair);
    }

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowingConsumer(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass,
        Pair<Invoker, List<String>> finallyBlockPair)
    {

        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object = new ThrowingObject<>(noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException);

        Invoker invoker = finallyBlockPair.getLeft();
        Function method = (ignored) -> {
            Consumer consumer = wrap((i) -> object.method(),
                expectedExceptionClass,
                rethrowsExpected,
                invoker);

            consumer.accept(null);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method, finallyBlockPair);
    }

    @ParameterizedTest
    @MethodSource("providerForThrowingTest")
    <E extends Exception, UE extends RuntimeException> void testTryCatchForThrowingSupplier(
        boolean noExceptionThrowing,
        boolean rethrowsExpected,
        Class<E> expectedExceptionClass,
        Class<UE> unexpectedExceptionClass,
        Pair<Invoker, List<String>> finallyBlockPair)
    {
        E expectedException = createEx(expectedExceptionClass);
        UE unexpectedException = createEx(unexpectedExceptionClass);
        final ThrowingObject<E, UE> object =
            new ThrowingObject<>(
                noExceptionThrowing, rethrowsExpected, expectedException, unexpectedException
            );

        Invoker invoker = finallyBlockPair.getLeft();
        Function method = (ignored) -> {
            rethrow(object::method, expectedExceptionClass, rethrowsExpected, invoker);
            return null;
        };

        testTryCatchLogic(rethrowsExpected, expectedExceptionClass, unexpectedException, method, finallyBlockPair);
    }

    void testTryCatchLogic(
        boolean rethrowsExpected,
        Class expectedExceptionClass,
        Exception unexpectedException,
        Function<?, ?> method,
        Pair<Invoker, List<String>> invoker
    )
    {
        Exception actual;
        try {
            method.apply(null);
        } catch (Exception e) {
            actual = e;
            Throwable cause = actual.getCause();
            if (unexpectedException != null) {
                String template = "Test: actual is %s, but expected is %s";
                printfln(template, simpleName(cause), expectedExceptionClass.getSimpleName());
                Assertions.assertNotEquals(expectedExceptionClass, cause.getClass());
                Assertions.assertEquals(unexpectedException.getClass(), cause.getClass());
            } else {
                if (rethrowsExpected) {
                    Assertions.assertEquals(expectedExceptionClass, cause.getClass());
                    String template = "Test: rethrown exception is '%s";
                    printfln(template, expectedExceptionClass.getSimpleName());
                }
            }

            if (invoker.getLeft() == EMPTY_FINALLY_BLOCK) {
                Assertions.assertTrue(invoker.getRight().isEmpty());
            } else {
                Assertions.assertTrue(invoker.getRight().contains(FINALLY));
            }
        }
    }

    private static Stream<Arguments> providerForThrowingTest() {
        List rethrowsExpected = List.of(true, false);
        List noExceptionThrowing = List.of(true, false);
        List expectedExceptions = List.of(SQLException.class, IOException.class, IllegalArgumentException.class);
        List unexpectedExceptions = CollectionUtils.arrayList(NumberFormatException.class, ArithmeticException.class, null);
        List<String> finallyBlockResult = new ArrayList<>();
        List<String> emptyFinallyBlockResult = new ArrayList<>();
        List finallyBlocks = CollectionUtils.arrayList(
            Pair.of(EMPTY_FINALLY_BLOCK, emptyFinallyBlockResult),
            Pair.of((Invoker) () -> finallyBlockResult.add(FINALLY), finallyBlockResult)
        );

        List<List> lists = NullableCartesianProduct.product(rethrowsExpected, noExceptionThrowing, expectedExceptions, unexpectedExceptions, finallyBlocks);
        return lists.stream().map(list -> Arguments.arguments(list.toArray()));
    }
}