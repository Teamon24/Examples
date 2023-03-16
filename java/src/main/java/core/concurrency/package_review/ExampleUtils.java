package core.concurrency.package_review;

import static utils.ConcurrencyUtils.threadPrintln;

public class ExampleUtils {
    public static void title(String message) {
        threadPrintln("-".repeat(100));
        threadPrintln(message);
        threadPrintln("-".repeat(100));
    }
}
