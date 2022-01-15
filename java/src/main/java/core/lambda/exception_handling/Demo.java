package core.lambda.exception_handling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static core.lambda.exception_handling.ThrowingFunction.wrap;

public class Demo {
    public static void main(String[] args) {

        Class<IOException> ioClass = IOException.class;
        Class<FileNotFoundException> fnfClass = FileNotFoundException.class;
        Class<ArithmeticException> aeClass = ArithmeticException.class;
        Class<OutOfMemoryError> oomClass = OutOfMemoryError.class;
        Class<ArrayIndexOutOfBoundsException> aioobClass = ArrayIndexOutOfBoundsException.class;

        ThrowingFunction<Object, ?, IOException>                    io     =  (ignored) -> checked(ioClass);
        ThrowingFunction<Object, ?, FileNotFoundException>          fnf    =  (ignored) -> checked(fnfClass);
        ThrowingFunction<Object, ?, IOException>                    ae     =  (ignored) -> unchecked(aeClass);
        ThrowingFunction<Object, ?, ArrayIndexOutOfBoundsException> aioob  =  (ignored) -> unchecked(aeClass);
        ThrowingFunction<Object, ?, OutOfMemoryError>               oom    =  (ignored) -> error(oomClass);

        Stream.of(1).forEach(it -> {
                try { wrap(io,    ioClass).apply(it);    } catch (Throwable t) { message(t); }
                try { wrap(fnf,   fnfClass).apply(it);   } catch (Throwable t) { message(t); }
                try { wrap(ae,    ioClass).apply(it);    } catch (Throwable t) { message(t); }
                try { wrap(aioob, aioobClass).apply(it); } catch (Throwable t) { message(t); }
                try { wrap(oom,   oomClass).apply(it);   } catch (Throwable t) { message(t); }
        });
    }

    private static void message(Throwable t) {
        System.out.println(t.getMessage());
    }

    public static <Ex extends RuntimeException> Object unchecked(Class<Ex> aClass) {
        throw createEx(aClass);
    }

    public static <Ex extends Exception> Object checked(Class<Ex> aClass) throws Ex {
        throw createEx(aClass);
    }

    public static <Ex extends Error> Object error(Class<Ex> aClass) {
        throw createEx(aClass);
    }

    public static <T> T createEx(Class<T> exceptionClass) {
        if (exceptionClass == null) return null;
        try {
            return exceptionClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
