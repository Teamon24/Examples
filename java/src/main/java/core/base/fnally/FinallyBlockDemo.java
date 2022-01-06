package core.base.fnally;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FinallyBlockDemo {

    public static void main(String args[]) {
        tryCatchFinally(catchBlock(), finallyBlock());
        tryCatchFinally(catchBlock(), finallyBlockThrowsException());
        tryCatchFinally(catchBlockThrowsException(), finallyBlock());
        tryCatchFinally(catchBlockThrowsException(), finallyBlockThrowsException());

        tryCatchAnotherExFinally(catchBlock(), finallyBlock());
    }

    private static Consumer<Exception> catchBlock() {
        return (e) -> System.out.printf("Catch block: exception handled - %s\n", e);
    }

    private static Consumer<Exception> catchBlockThrowsException() {
        return (e) -> {
            System.out.printf("Catch block: %s was caught\n", e);
            System.out.println("Catch block: throwing an exception");
            throw new RuntimeException("Exception was thrown in catch block");
        };
    }

    private static Supplier<?> finallyBlock() {
        return () -> {
            System.out.println("Finally block: execution was done");
            System.out.println();
            return null;
        };
    }

    private static Supplier<?> finallyBlockThrowsException() {
        return () -> {
            System.out.println("Finally block: throwing an exception");
            System.out.println();
            throw new RuntimeException("Exception was thrown in finally block");
        };
    }

    public static void tryCatchFinally(Consumer<Exception> catchBlock, Supplier<?> finallyBlock) {
        try {
            try { tryBlockAction(); } catch (ArithmeticException e) { catchBlock.accept(e); } finally { finallyBlock.get(); }
        } catch (Throwable throwable) {
            String simpleName = throwable.getClass().getSimpleName();
            System.out.printf("Global Try Block: %s was caught (message: \"%s\")\n", simpleName, throwable.getMessage());
            System.out.println();
        }
    }

    private static void tryCatchAnotherExFinally(final Consumer<Exception> catchBlock, Supplier<?> finallyBlock) {
        try {
            try { tryBlockAction(); } catch (NullPointerException npe) { catchBlock.accept(npe); } finally { finallyBlock.get(); }
        } catch (Throwable throwable) {
            String simpleName = throwable.getClass().getSimpleName();
            System.out.printf("Global Try Block: NullPointerException was expected in catch block, but actual - %s\n", simpleName);
            System.out.println();
        }
    }

    private static void tryBlockAction() {
        System.out.println("------------------------------------");
        System.out.println("Try block: dividing by zero");
        int data = 25 / 0;
        System.out.println(data);
    }
}
