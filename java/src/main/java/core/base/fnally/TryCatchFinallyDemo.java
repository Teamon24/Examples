package core.base.fnally;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.collect.Lists.cartesianProduct;
import static com.google.common.collect.Lists.newArrayList;
import static core.base.fnally.CatchBlock.catchBlock;
import static core.base.fnally.FinallyBlock.finallyBlock;
import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;
import static java.lang.System.out;

public class TryCatchFinallyDemo {

    protected static final int FINALLY_RETURN = -2;
    protected static final int CATCH_RETURN = -1;

    public static void main(String args[]) {
        Supplier<Integer> divideByZero = () ->  TryCatchFinallyDemo.divide(1, 0);
        Supplier<Integer> divide = () ->  TryCatchFinallyDemo.divide(100, 25);

        Class<NullPointerException> npe = NullPointerException.class;
        Class<ArithmeticException> ae = ArithmeticException.class;

        cartesianProduct(
            newArrayList(divide, divideByZero),
            newArrayList(catchBlock(CATCH_RETURN), catchBlock(rte("catch"))),
            newArrayList(finallyBlock(), finallyBlock(FINALLY_RETURN), finallyBlock(rte("finally"))),
            newArrayList(ae, npe)
        ).forEach(objects ->
            tryBlock(
                (Supplier<Integer>) objects.get(0),
                (CatchBlock<Integer>) objects.get(1),
                (FinallyBlock<Integer>) objects.get(2),
                (Class<? extends Exception>) objects.get(3)
            )
        );
   }

    public static <T> T tryBlock(
        Supplier<T> tryBlock,
        CatchBlock<T> catchBlock,
        FinallyBlock<T> finallyBlock,
        Class<? extends Exception> catchBlockExpectedExceptionClass
    ) {
        try {
            Pair<T, String> resultAndMessage = tryCatchFinally(tryBlock,
                catchBlock,
                finallyBlock,
                catchBlockExpectedExceptionClass);

            printfln(Global("Try", "expected exception in catch %s"), catchBlockExpectedExceptionClass.getSimpleName());
            printfln(Global("Try", "%s\n\n"), resultAndMessage.getRight());
            return resultAndMessage.getLeft();
        } catch (Throwable throwable) {
            printfln(
                Global("Catch", "%s (%s) was handled\n\n"),
                simpleName(throwable),
                throwable.getMessage());
        }
        return null;
    }

    private static <T> Pair<T, String> tryCatchFinally(
        Supplier<T> tryBlock,
        Function<Exception, T> catchBlock,
        FinallyBlock<T> finallyBlock,
        Class<? extends Exception> expectedExceptionClass
    ) {
        try {
            T result = tryBlock.get();
            return Pair.of(result, returnMessage("try", result));
        } catch (Exception e) {
            try {
                expectedExceptionClass.cast(e);
            } catch (ClassCastException ex) {
                throw e;
            }
            T result = catchBlock.apply(e);
            return Pair.of(result, returnMessage("catch", result));
        } finally {
            T result = finallyBlock.get();
            if (finallyBlock.returns()) {
                return Pair.of(result, returnMessage("finally", result));
            }
        }
    }

    private static String Global(String blockName, String blockMessage) {
        return "|Global " + blockName + " Block|: " + blockMessage;
    }

    private static <T> String returnMessage(String blockName, T result) {
        return String.format(blockName + " block returned value: %s", result);
    }

    private static int divide(int a, int value) {
        out.println("------------------------------------");
        printfln("Try block: %s / %s", a, value);
        int result = a / value;
        printfln("Try block: %s / %s = %s", a, value, result);
        return result;
    }

    private static Supplier<RuntimeException> rte(String blockName) {
        return () -> new RuntimeException("thrown in " + blockName + " block");
    }
}
