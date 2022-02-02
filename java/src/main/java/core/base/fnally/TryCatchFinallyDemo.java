package core.base.fnally;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;
import java.util.function.Supplier;

import static core.base.fnally.CatchBlock.catchBlock;
import static core.base.fnally.FinallyBlock.finallyBlock;
import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;
import static utils.PrintUtils.println;

public class TryCatchFinallyDemo {

    protected static final int FB = -2;
    protected static final int CB = -1;

    public static void main(String args[]) {
        Supplier<Integer> divideByZero = () ->  TryCatchFinallyDemo.divide(1, 0);
        Supplier<Integer> divide = () ->  TryCatchFinallyDemo.divide(100, 25);

        Class<NullPointerException> npe = NullPointerException.class;
        Class<ArithmeticException> ae = ArithmeticException.class;

        tryBlock(divide,       catchBlock(CB),            finallyBlock(),               ae);
        tryBlock(divideByZero, catchBlock(CB),            finallyBlock(),               ae);
        tryBlock(divide,       catchBlock(CB),            finallyBlock(),               ae);
        tryBlock(divide,       catchBlock(CB),            finallyBlock(FB),             ae);
        tryBlock(divideByZero, catchBlock(CB),            finallyBlock(),               ae);
        tryBlock(divideByZero, catchBlock(CB),            finallyBlock(FB),             ae);
        tryBlock(divideByZero, catchBlock(rte("catch")),  finallyBlock(FB),             ae);
        tryBlock(divideByZero, catchBlock(CB),            finallyBlock(rte("finally")), ae);
        tryBlock(divideByZero, catchBlock(rte("catch")),  finallyBlock(rte("finally")), ae);
        tryBlock(divideByZero, catchBlock(CB),            finallyBlock(FB),             npe);
        tryBlock(divideByZero, catchBlock(rte("catch")),  finallyBlock(rte("finally")), npe);
    }

    public static <T> T tryBlock(
        Supplier<T> tryBlock,
        CatchBlock<T> catchBlock,
        FinallyBlock<T> finallyBlock,
        Class<? extends Exception> expectedExceptionClass
    ) {
        try {
            Pair<T, String> resultAndMessage = tryCatchFinally(tryBlock, catchBlock, finallyBlock, expectedExceptionClass);
            printfln(Global("Try") + ": %s\n\n", resultAndMessage.getRight());
            return resultAndMessage.getLeft();
        } catch (Throwable throwable) {
            printfln(
                Global("Catch") + ": %s (\"%s\") was handled\n\n",
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

    private static String Global(String blockName) {
        return "|Global " + blockName + " Block|";
    }

    private static <T> String returnMessage(String blockName, T result) {
        return String.format(blockName + " block returned value: %s", result);
    }

    private static int divide(int a, int value) {
        println("------------------------------------");
        printfln("Try block: %s / %s", a, value);
        int result = a / value;
        printfln("Try block: %s / %s = %s", a, value, result);
        return result;
    }

    private static Supplier<RuntimeException> rte(String blockName) {
        return () -> new RuntimeException("thrown in " + blockName + " block");
    }
}
