package core.lambda.exception_handling;

import java.lang.reflect.InvocationTargetException;

class ThrowingObject<Expected extends Exception, Unexpected extends RuntimeException> {

    private boolean noExceptionalCase;
    private boolean throwsException;
    Expected expectedException;
    Unexpected unexpectedException;

    public ThrowingObject(
        boolean noExceptionalCase,
        boolean throwsException,
        Expected expectedException)
    {
        this.noExceptionalCase = noExceptionalCase;
        this.throwsException = throwsException;
        this.expectedException = expectedException;
    }

    public ThrowingObject(
        boolean noExceptionalCase,
        boolean throwsException,
        Expected expectedException,
        Unexpected unexpectedException)
    {
        this.noExceptionalCase = noExceptionalCase;
        this.throwsException = throwsException;
        this.expectedException = expectedException;
        this.unexpectedException = unexpectedException;
    }

    public void method() throws Expected {
        if (noExceptionalCase) return;

        if (this.unexpectedException != null) {
            throw this.unexpectedException;
        }

        if (this.throwsException) {
            throw this.expectedException;
        }
    }

    public static <T extends Exception> T createEx(Class<T> exceptionClass) {
        if (exceptionClass == null) return null;
        try {
            return exceptionClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}