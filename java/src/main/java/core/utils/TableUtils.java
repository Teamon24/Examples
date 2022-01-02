package core.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TableUtils {

    public static final String SEPARATOR = "     ";
    public static final Random RANDOM = new Random();
    public static final String LINE_ELEMENT = "-";

    public record Table(List<String> columns, List<Record> records) {
        public void add(Record record) { records.add(record); }
    }
    public record Record(Map<String, Object> values) {}

    public static void main(String[] args) {
        List<String> columns = List.of("Threads", "Expected", "Actual", "Lost");
        List<Record> records = IntStream.range(0, 10)
            .mapToObj(
                i -> new Record(
                    columns.stream()
                        .map(column -> Pair.of(column, String.valueOf(RANDOM.nextInt(1_000_000))))
                        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight))
                )
            ).toList();
        Table table = new Table(columns, records);
        TableUtils.printResults(table);
    }

    public static void printResults(Table table) {
        Map<String, Integer> longestValuesByColumns = getLengthOfLongestValuesForColumns(table);
        StringBuilder stringBuilder = new StringBuilder();
        createColumnsTitle(stringBuilder, table, longestValuesByColumns);
        table.records.forEach(record -> createRecordString(stringBuilder, record, table, longestValuesByColumns));
        System.out.println(stringBuilder);
    }

    private static void createRecordString(final StringBuilder stringBuilder,
                                           final Record record,
                                           final Table table,
                                           final Map<String, Integer> longestValuesByColumns)
    {
        List<Pair<String, Object>> orderedValues = record.values.entrySet().stream()
            .sorted(Comparator.comparing(entry -> table.columns.indexOf(entry.getKey())))
            .map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList();

        appendSeparator(stringBuilder);
        for (int i = 0, orderedValuesSize = orderedValues.size(); i < orderedValuesSize; i++) {
            final Pair<String, Object> value = orderedValues.get(i);
            Object recordValue = value.getRight();
            String column = value.getLeft();
            String indent = getIndent(longestValuesByColumns, column, recordValue);
            if (i == 0 ) {
                stringBuilder.append(recordValue).append(indent);
            } else {
                stringBuilder.append(indent).append(recordValue);
            }
            appendSeparator(stringBuilder);
        }
        stringBuilder.append("\n");
        appendLine(stringBuilder, longestValuesByColumns);


    }

    private static void createColumnsTitle(final StringBuilder stringBuilder,
                                           final Table table,
                                           final Map<String, Integer> longestValuesByColumns)
    {
        appendLine(stringBuilder, longestValuesByColumns);
        appendSeparator(stringBuilder);
        for (String column : table.columns) {
            stringBuilder
                .append(getIndent(longestValuesByColumns, column))
                .append(column);
            appendSeparator(stringBuilder);
        }
        stringBuilder.append("\n");
        appendLine(stringBuilder, longestValuesByColumns);
    }

    private static void appendLine(final StringBuilder stringBuilder,
                                   final Map<String, Integer> longestValuesByColumns)
    {
        int separatorLength = SEPARATOR.length();
        int separatorsAmount = longestValuesByColumns.size() + 1;
        Integer valuesLength = longestValuesByColumns.values().stream().reduce((acc, next) -> acc = acc + next).stream().findFirst().get();
        stringBuilder.append(
            LINE_ELEMENT.repeat(
                valuesLength + separatorLength * separatorsAmount
            )
        );
        stringBuilder.append("\n");
    }

    private static void appendSeparator(final StringBuilder stringBuilder) {
        stringBuilder.append(SEPARATOR);
    }

    private static String getIndent(final Map<String, Integer> longestValuesByColumns,
                                    final String column)
    {
        return " ".repeat(longestValuesByColumns.get(column) - column.length());
    }

    private static  String getIndent(final Map<String, Integer> longestValuesByColumns,
                                    final String column,
                                    final Object value)
    {
        return " ".repeat(longestValuesByColumns.get(column) - value.toString().length());
    }

    private static Map<String, Integer> getLengthOfLongestValuesForColumns(final Table table) {
        return table.columns.stream().map(column -> {
            final List valuesByColumn = getValuesByColumn(table, column);
            valuesByColumn.add(column);
            final Integer lengthOfLongestColumnValue = getLengthOfLongestColumnValue(valuesByColumn);
            return Pair.of(column, lengthOfLongestColumnValue);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static Integer getLengthOfLongestColumnValue(final List valuesByColumn) {
        return valuesByColumn.stream()
            .max(Comparator.comparingInt(object -> object.toString().length()))
            .stream().findFirst().get()
            .toString().length();
    }

    private static List getValuesByColumn(final Table table, final String column) {
        return table.records.stream().map(record -> record.values.get(column)).collect(Collectors.toList());
    }
}
