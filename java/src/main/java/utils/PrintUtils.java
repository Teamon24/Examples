package utils;

/**
 *
 */
public class PrintUtils {
    public static void printfln(String template, Object... args) {
        System.out.printf(template + "\n", args);
    }

    public static void println(Object message) {
        System.out.println(message);
    }

    public static void println() {
        System.out.println();
    }
}
