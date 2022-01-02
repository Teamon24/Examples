package core.base.finlly;

import java.util.function.Consumer;

public class FinallyBlockDemo {
    public static void main(String args[]) {
        tryCatchFinally(catchBlock());
        tryCatchFinally(catchBlockThrowsException());
        tryCatchAnotherExFinally(catchBlock());
    }

    private static Consumer<Exception> catchBlock() {
        return (e) -> System.out.printf("Exception handled: %s\n", e);
    }

    private static Consumer<Exception> catchBlockThrowsException() {
        return (e) -> {
            System.out.printf("Exception handled: %s\n", e);
            throw new RuntimeException();
        };
    }

    public static void tryCatchFinally(Consumer<Exception> catchBlock)
    {
        try {
            try {
                catchBlockAction();
            } catch (ArithmeticException e) {
                catchBlock.accept(e);
            } finally {
                finallyBlockMessage();
            }
        } catch (Throwable throwable) {
            System.out.printf("%s in catch block was thrown\n", throwable.getClass().getSimpleName());
            System.out.println();
        }
    }

    private static void tryCatchAnotherExFinally(final Consumer<Exception> catchBlock) {
        try {
            try {
                catchBlockAction();
            } catch (NullPointerException npe) {
                catchBlock.accept(npe);
            } finally {
                finallyBlockMessage();
            }
        } catch (Throwable throwable) {
            System.out.printf(
                "NullPointerException is in a catch expected, but actual is %s\n",
                throwable.getClass().getSimpleName()
            );
            System.out.println();
        }
    }

    private static void catchBlockAction() {
        System.out.println("------------------------------------");
        System.out.println("Inside the try block");
        int data = 25 / 0;
        System.out.println(data);
    }

    private static void finallyBlockMessage() {
        System.out.println("finally block was executed");
        System.out.println();
    }
}
