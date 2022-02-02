package core.exceptions;

import java.io.FileNotFoundException;

import static utils.PrintUtils.println;

public interface Handler {
    void throwable() throws Throwable;
    void checked() throws Exception;
    <T extends RuntimeException> void  unchecked() throws T;
}

class HandlerImpl implements Handler {

    public void throwable() throws RuntimeException, Exception {
        println("throws Throwable descendants");
    }

    @Override
    public void checked() throws FileNotFoundException, ClassNotFoundException  {
        println("throws Exception descendants");
    }

    @Override
    public void unchecked() throws ArithmeticException, ArrayIndexOutOfBoundsException  {
        println("throws RuntimeException descendants");
    }
}
