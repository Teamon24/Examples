package core.lambda.exception_handling;

import utils.ClassUtils;

import java.util.List;

import static utils.PrintUtils.printfln;
import static java.lang.System.out;

public final class ThrowingLambdasEssential {

    public static <E extends Throwable> void rethrowIfAnotherIsCaught(
        Class<E> expectedClass,
        Throwable actualException,
        boolean rethrows)
    {
        try {
            expectedClass.cast(actualException);
            printSuccessCaughtMessage(expectedClass, actualException);
            rethrowIfNeeded(rethrows, actualException);
        } catch (ClassCastException ccEx) {
            printComparisonFailMessage(List.of(expectedClass), actualException);
            throwRTE(actualException);
        }
    }

    private static void rethrowIfNeeded(boolean rethrows, Throwable actualException) {
        if (rethrows) {
            String template = "Lambda: rethrow caught exception: '%s'.";
            printfln(template, ClassUtils.simpleName(actualException));
            throwRTE(actualException);
        };
    }

    public static <E extends Throwable> void rethrowOrThrowActual(
        Class<E> expectedClass,
        Throwable actualException) throws RuntimeException
    {
        try {
            expectedClass.cast(actualException);
            throwRTE(actualException);
        } catch (ClassCastException ccEx) {
            printComparisonFailMessage(List.of(expectedClass), actualException);
            throwRTE(actualException);
        }
    }

    public static <E extends Throwable> void rethrowAnotherIfWasCaught(
        List<Class<? extends E>> expectedClasses,
        Throwable actualException,
        boolean toThrowFurther)
    {
        boolean found = false;
        Throwable actual;
        E expectedException = null;
        for (Class<? extends E> it : expectedClasses) {
            try {
                expectedException = it.cast(actualException);
                found = true;
            } catch (ClassCastException ignored) {}
        }

        if (found) {
            System.err.println("Exception occurred : " + expectedException.getMessage());
            rethrowIfNeeded(toThrowFurther, actualException);
        } else {
            printComparisonFailMessage(expectedClasses, actualException);
            throwRTE(actualException);
        }
    }

    private static <E extends Throwable> void printComparisonFailMessage(
        List<Class<? extends E>> expectedClasses,
        Throwable actualException)
    {
        String template = "Lambda: %s was thrown but %s was expected. Throwing actual exception further.";
        String message = String.format(template,
            ClassUtils.simpleName(actualException),
            ClassUtils.joinSimpleNames(expectedClasses)
        );
        out.println(message);
    }

    private static <E extends Throwable> void printSuccessCaughtMessage(
        Class<E> expectedClass,
        Throwable actualException)
    {
        String template = "Lambda: expected '%s' and actual '%s' classes are identical.";
        String message = String.format(template, expectedClass.getSimpleName(), ClassUtils.simpleName(actualException));
        System.err.println(message);
    }

    private static void throwRTE(Throwable ex) {
        throw new RuntimeException(ex);
    }
}
