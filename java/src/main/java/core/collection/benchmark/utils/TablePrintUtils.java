package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static utils.PrintUtils.println;

public final class TablePrintUtils {

    public static void printTable(List<AveragedMethodResult<?>> results) {
        if (results.isEmpty()) System.out.println("Empty table");

        HashMap<String, Integer> titlesAndIndent = new LinkedHashMap<>() {{
            put("TYPE", 16);
            put("METHOD", 10);
            put("INDEX", 10);
            put("ELEMENT", 8);
            put("AVERAGE TIME", 12);
        }};

        reassignValues(titlesAndIndent);

        Integer[] indents = new Integer[titlesAndIndent.size()];
        titlesAndIndent.values().toArray(indents);
        String line = "-".repeat(sum(indents) + indents.length);

        String title = getTitleLine(titlesAndIndent, indents);

        println(line);
        println(title);
        println(line);

        String rowFormat = getRowFormat(indents);
        for(AveragedMethodResult<?> result: results) {
            System.out.format(
                rowFormat,
                result.getCollectionClass(),
                result.getMethodType(),
                result.getIndex(),
                result.getElement(),
                result.getAverageExecutionTime()
            );
            println();
        }
        println(line);
    }

    private static void reassignValues(final HashMap<String, Integer> titlesAndIndent) {
        titlesAndIndent.forEach((title, indent) -> titlesAndIndent.put(title, Math.max(title.length(), indent)));
    }

    private static String getTitleLine(final HashMap<String, Integer> titlesAndIndent, final Integer[] indents) {
        String[] titles = new String[titlesAndIndent.size()];
        titlesAndIndent.keySet().toArray(titles);
        String title = getTitle(titles, indents);
        return title;
    }

    private static Integer sum(final Integer[] indents) {
        return Arrays.stream(indents).reduce(Integer::sum).get();
    }

    private static String getTitle(final String[] titles, final Integer[] indents) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indents.length; i++) {
            stringBuilder
                .append(" ".repeat(indents[i] - titles[i].length()))
                .append(titles[i])
                .append(" ");
        }
        String title = stringBuilder.toString();
        return title;
    }

    private static String getRowFormat(final Integer ... indent) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(indent).limit(indent.length - 1).forEach(it -> stringBuilder.append(it).append("s %"));
        return "%" + stringBuilder + String.format("%s.5f", indent[indent.length - 1]);
    }

}
