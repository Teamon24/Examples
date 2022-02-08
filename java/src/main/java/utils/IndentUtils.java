package utils;

import core.collection.benchmark.utils.MaxUtils;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public final class IndentUtils {

    public static <T> String withIndent(final T currentValue, final int lengthOfMaxValueToString) {
        return currentValue + " ".repeat(Math.abs(lengthOfMaxValueToString - currentValue.toString().length()));
    }

    public static <T> String getIndent(final T currentValue, final int lengthOfMaxValueToString) {
        return " ".repeat(Math.abs(lengthOfMaxValueToString - currentValue.toString().length()));
    }

    public static String addIndent(String string, List<String> allPossibleStrings) {
        int indent = MaxUtils.longest(allPossibleStrings).length();
        return addIndent(string, indent);
    }

    public static String addIndent(String string, String[] allPossibleStrings) {
        int indent = MaxUtils.longest(Arrays.asList(allPossibleStrings)).length();
        return addIndent(string, indent);
    }

    public static String addIndent(String string, int indent) {
        return string + getIndent(string, indent);
    }
}
