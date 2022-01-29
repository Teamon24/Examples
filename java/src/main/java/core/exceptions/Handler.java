package core.exceptions;

public interface Handler {
    void handle();
    void handle2() throws Throwable;
}

class HandlerImpl implements Handler {
    public void handle() throws RuntimeException {
        System.out.println("Handling");
    }

    public void handle2() throws Throwable {
        System.out.println("Handling 2");
    }
}
