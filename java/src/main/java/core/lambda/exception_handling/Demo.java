package core.lambda.exception_handling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Demo {
    public static void main(String[] args) {

        Class<IOException>                    ioClass    = IOException.class;
        Class<FileNotFoundException>          fnfClass   = FileNotFoundException.class;
        Class<ArithmeticException>            aeClass    = ArithmeticException.class;
        Class<OutOfMemoryError>               oomClass   = OutOfMemoryError.class;
        Class<ArrayIndexOutOfBoundsException> aioobClass = ArrayIndexOutOfBoundsException.class;

        ThrowingFunction<Object, ?, IOException>                    ioFunc    = (ignored) -> getChecked(ioClass);
        ThrowingFunction<Object, ?, FileNotFoundException>          fnfFunc   = (ignored) -> getChecked(fnfClass);
        ThrowingFunction<Object, ?, IOException>                    aeFunc    = (ignored) -> getUnchecked(aeClass);
        ThrowingFunction<Object, ?, ArrayIndexOutOfBoundsException> aioobFunc = (ignored) -> getUnchecked(aeClass);
        ThrowingFunction<Object, ?, OutOfMemoryError>               oomFunc   = (ignored) -> getError(oomClass);

        Stream.of(1).forEach(it -> {
                try { ThrowingFunction.wrap(ioFunc,    ioClass).apply(it);    } catch (Throwable t) { message(t); }
                try { ThrowingFunction.wrap(fnfFunc,   fnfClass).apply(it);   } catch (Throwable t) { message(t); }
                try { ThrowingFunction.wrap(aeFunc,    ioClass).apply(it);    } catch (Throwable t) { message(t); }
                try { ThrowingFunction.wrap(aioobFunc, aioobClass).apply(it); } catch (Throwable t) { message(t); }
                try { ThrowingFunction.wrap(oomFunc,   oomClass).apply(it);   } catch (Throwable t) { message(t); }
        });
    }

    private static void message(Throwable t) {
        out.println(t.getMessage());
    }

    public static <Ex extends RuntimeException> Object getUnchecked(Class<Ex> aClass) {
        throw createEx(aClass);
    }

    public static <Ex extends Exception> Object getChecked(Class<Ex> aClass) throws Ex {
        throw createEx(aClass);
    }

    public static <Ex extends Error> Object getError(Class<Ex> aClass) {
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
