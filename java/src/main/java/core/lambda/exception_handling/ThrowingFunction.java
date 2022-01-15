package core.lambda.exception_handling;

import java.util.function.Function;

import static core.lambda.exception_handling.ThrowingLambdasEssential.rethrowOrThrowActual;

@FunctionalInterface
public interface ThrowingFunction<In, Out, E extends Throwable> {
    Out apply(In arg) throws E;

    static <Arg1, Out, Ex extends Throwable> Function<Arg1, Out> wrap(
        ThrowingFunction<Arg1, Out, Ex> throwingFunction,
        Class<Ex> expectedExceptionClass)
    {
        return (arg) -> {
            try {
                return throwingFunction.apply(arg);
            } catch (Throwable actualException) {
                rethrowOrThrowActual(expectedExceptionClass, actualException);
            }
            return null;
        };
    }
}