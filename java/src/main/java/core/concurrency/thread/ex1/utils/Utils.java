package core.concurrency.thread.ex1.utils;

import core.concurrency.thread.ex1.state.StateObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;

public class Utils {

    static String repeatSymbolsByLengthDiff(final String firstString,
                                            final String secondString,
                                            final String symbol)
    {
        return symbol.repeat(Math.abs(firstString.length() - secondString.length()));
    }

    public static void printResults(final StateObject stateObject,
                                    HashMap<Pair<String, Integer>, String> orderedRowsAndValues)
    {

        String longestRowName = orderedRowsAndValues.keySet().stream()
            .map(Pair::getKey)
            .max(Comparator.comparing(String::length)).get();

        Integer digitsOfLongestRowName = longestRowName.length();

        String longestValueString = orderedRowsAndValues.values().stream()
            .max(Comparator.comparing(String::length)).get();


        StringBuilder stringBuilder = getBuilderWithTitle(longestRowName, digitsOfLongestRowName, stateObject);

        orderedRowsAndValues.entrySet().stream()
            .map(entry -> {
                String rowName = entry.getKey().getLeft();
                final String value = entry.getValue();
                Integer order = entry.getKey().getRight();
                return Pair.of(order, getResultString(longestRowName, longestValueString, rowName, value));
            }).sorted(Comparator.comparing(Pair::getLeft))
            .forEach(pair -> stringBuilder.append(pair.getRight()));

        System.out.println(stringBuilder);
    }

    private static StringBuilder getBuilderWithTitle(final String longestRowName,
                                                     final Integer digitsOfLongestRowName,
                                                     final StateObject stateObject)
    {
        String stateObjectName = stateObject.getClass().getSimpleName();
        String repeat = "-".repeat(longestRowName.length() + digitsOfLongestRowName + 2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s\n%s\n%s\n", repeat, stateObjectName, repeat));
        return stringBuilder;
    }

    private static String getResultString(final String longestRowName,
                                          final String longestValueString,
                                          final String rowName,
                                          final String value)
    {
        return new StringBuilder()
            .append(repeatSymbolsByLengthDiff(rowName, longestRowName, " "))
            .append(rowName)
            .append(": ")
            .append(repeatSymbolsByLengthDiff(value, longestValueString, " "))
            .append(value)
            .append("\n").toString();
    }
}
