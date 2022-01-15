package core.lambda.exception_handling;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class ThrowingLambdasEssential {

    public static <E extends Throwable> void throwAnotherIfWasCaught(
        Class<E> expectedClass,
        Throwable actualException,
        boolean rethrows)
    {
        try {
            E expectedException = expectedClass.cast(actualException);
            System.err.println("Exception occurred : " + expectedException.getMessage());
            if (rethrows) throwRTE(actualException);
        } catch (ClassCastException ccEx) {
            printComparisonFailMessage(List.of(expectedClass), actualException);
            throwRTE(actualException);
        }
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
        List<Class<E>> expectedClasses,
        Throwable actualException,
        boolean toThrowFurther)
    {
        boolean found = false;
        Throwable actual;
        E expectedException = null;
        for (Class<E> it : expectedClasses) {
            try {
                expectedException = it.cast(actualException);
                found = true;
            } catch (ClassCastException ignored) {}
        }

        if (found) {
            System.err.println("Exception occurred : " + expectedException.getMessage());
            if (toThrowFurther) throwRTE(actualException);
        } else {
            printComparisonFailMessage(expectedClasses, actualException);
            throwRTE(actualException);
        }
    }

    private static <E extends Throwable> void printComparisonFailMessage(
        List<Class<E>> expectedClasses,
        Throwable actualException)
    {
        String template = "%s was expected but %s was thrown. Throwing actual exception further.";
        String message = template.formatted(
            StringUtils.joinWith(", ", expectedClasses.stream().map(Class::getSimpleName).toList()),
            actualException.getClass().getSimpleName()
        );
        System.out.println(message);
    }

    private static void throwRTE(Throwable ex) {
        throw new RuntimeException(ex);
    }
}
