package core.exceptions;

import utils.RandomUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

/**
 * <pre>
 * {@code
 *               Object
 *                  |
 *   +----------Throwable----------------+
 *   |                                   |
 * Error                       +----Exception (checked)-------+
 *                             |                              |
 *                      RuntimeException (unchecked)
 * }
 * </pre>
 */
public interface Handler {
    void throwable() throws Throwable;
    void checked() throws Exception;
    void unchecked() throws RuntimeException;
    void unchecked2();
}

class MyException extends RuntimeException {
    public MyException(Exception e, String message) {}
}

/**
 * При указании <strong>throws</strong> раскрывается инкапсуляция,
 * поэтому использование <strong>throws</strong> считается не best practice.
 */
class HandlerImpl implements Handler {

    public static void main(String[] args) {
        method(new String[]{});
    }

    public static void method(String[] args) {
        HandlerImpl handler = new HandlerImpl();
        try {
            handler.checked();
        } catch (Exception e) {
            String message = "some reason to throw " + MyException.class.getSimpleName();
            throw new MyException(e, message);
        }

        handler.unchecked();
        handler.unchecked2();
    }

    public void throwable() throws Throwable {
        int random = RandomUtils.random(1, 2);
        if (random == 1) throw new FileNotFoundException();
        if (random == 2) throw new RuntimeException();
    }

    @Override
    public void checked() throws Exception  {
        throw new FileNotFoundException();
    }

    @Override
    public void unchecked() {
        throw new RuntimeException();
    }

    @Override
    public void unchecked2() throws RuntimeException {
        throw new ArrayIndexOutOfBoundsException();
    }
}
