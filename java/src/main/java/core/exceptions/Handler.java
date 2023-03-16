package core.exceptions;

import utils.PrintUtils;

import java.io.FileNotFoundException;

import static java.lang.System.out;

public interface Handler {
    void throwable() throws Throwable;
    void checked() throws Exception;
    <T extends RuntimeException> void  unchecked() throws T;
}

class HandlerImpl implements Handler {

    public void throwable() throws RuntimeException, Exception {
        System.out.println("throws Throwable descendants");
    }

    @Override
    public void checked() throws FileNotFoundException, ClassNotFoundException  {
        System.out.println("throws Exception descendants");
    }

    @Override
    public void unchecked() throws ArithmeticException, ArrayIndexOutOfBoundsException  {
        System.out.println("throws RuntimeException descendants");
    }
}
