package utils;

public final class ExceptionUtils {
    public static void throwIf(boolean conditionIsTrue, String message) {
        if (conditionIsTrue) {
            throw new RuntimeException(message);
        }
    }
}
