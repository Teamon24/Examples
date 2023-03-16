package utils;

public final class PrintUtils {
    public static void printfln(String template, Object... args) {
        System.out.printf(template + "\n", args);
    }
}
