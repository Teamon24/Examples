package thread.ex1;

import static java.lang.Math.abs;

public class Utils {

    public static String getSymbols(final int expected,
                                    final int actual,
                                    final String symbol) {
        int diff = IntegerUtils.countDigits(expected) - IntegerUtils.countDigits(actual);
        return symbol.repeat(diff);
    }

    static String getSymbols(final String firstString,
                             final String secondString,
                             final String symbol) {
        return symbol.repeat(firstString.length() - secondString.length());
    }

    static void printResults(final int actual,
                             final int expected,
                             final StateObject stateObject,
                             int doubleAccuracy)
    {
        Double loss = 100 - ((double) actual) / expected * 100;

        String lossString1 = String.format("!.%sf", doubleAccuracy).replace("!", "%");
        String lossString = String.format(lossString1, loss);

        String expectedString = "Expected";
        Integer expectedDigitsNumber = IntegerUtils.countDigits(expected);
        String actualString = "Actual";
        String lostString = "Lost";

        String stateObjectName = stateObject.getClass().getSimpleName();

        String repeat = "-".repeat(expectedString.length() + expectedDigitsNumber + 2);
        String titleMessage = String.format("%s\n%s\n%s\n", repeat, stateObjectName, repeat);

        String template = String.format(
            expectedString + ": &\n" +
                "&" + actualString + ": &&\n" +
                "&" + lostString + ": &*%sf\n\n", doubleAccuracy).replace("&", "%s").replace("*", "%.");

        String message = String.format(template,
            expected,
            getSymbols(expectedString, actualString, " "),
            getSymbols(expected, actual, " "),
            actual,
            getSymbols(expectedString, lostString, " "),
            " ".repeat(expectedDigitsNumber - lossString.length()),
            loss);

        System.out.println(titleMessage +"\n" + message);
    }
}
