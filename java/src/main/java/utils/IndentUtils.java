package utils;

import core.collection.benchmark.utils.MaxUtils;

import java.util.Arrays;

public final class IndentUtils {
    public static <T> String getIndent(final T currentValue, final int lengthOfMaxValueToString) {
        return " ".repeat(lengthOfMaxValueToString - currentValue.toString().length());
    }

    public static String addIndent(String string, String[] allPossibleStrings) {
        int indent = MaxUtils.longest(Arrays.asList(allPossibleStrings)).length();
        return addIndent(string, indent);
    }

    public static String addIndent(String string, int indent) {
        return string + getIndent(string, indent);
    }
}
