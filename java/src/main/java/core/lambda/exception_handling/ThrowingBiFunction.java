package core.lambda.exception_handling;

import java.util.function.BiFunction;

import static core.lambda.exception_handling.ThrowingLambdasEssential.*;

@FunctionalInterface
public interface ThrowingBiFunction<Arg1, Arg2, Out, Ex extends Throwable> {
    Out apply(Arg1 arg1, Arg2 arg2) throws Ex;

    static <Arg1, Arg2, Out, Ex extends Throwable> BiFunction<Arg1, Arg2, Out> wrap(
        ThrowingBiFunction<Arg1, Arg2, Out, Ex> throwingBiFunction,
        Class<Ex> expectedExceptionClass)
    {
        return (arg1, arg2) -> {
            try {
                return throwingBiFunction.apply(arg1, arg2);
            } catch (Throwable actualException) {
                rethrowOrThrowActual(expectedExceptionClass, actualException);
            }
            return null;
        };
    }

    static <Arg1, Arg2, Out, Ex extends Throwable> BiFunction<Arg1, Arg2, Out> wrap(
        BiFunction<Arg1, Arg2, Out> biFunction,
        Class<Ex> expectedExceptionClass)
    {
        return (arg1, arg2) -> {
            try {
                return biFunction.apply(arg1, arg2);
            } catch (Throwable actualException) {
                rethrowOrThrowActual(expectedExceptionClass, actualException);
            }
            return null;
        };
    }
}
