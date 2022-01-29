package core.collection.benchmark.utils;

import org.apache.commons.lang3.StringUtils;
import utils.ConcurrencyUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class PrintResultBuilder<T> {
    private Integer testAmount;
    private Integer testNumber;
    private String collection;
    private String method;
    private Integer index;
    private T element;
    private Long executionTime;

    public PrintResultBuilder<T> testAmount(int testsAmount) {
        this.testAmount = testsAmount;
        return this;
    }

    public PrintResultBuilder<T> testNumber(Integer testNumber) {
        this.testNumber = testNumber;
        return this;
    }

    public PrintResultBuilder<T> collection(String collection) {
        this.collection = collection;
        return this;
    }

    public PrintResultBuilder<T> method(String method) {
        this.method = method;
        return this;
    }

    public PrintResultBuilder<T> index(Integer index) {
        this.index = index;
        return this;
    }

    public PrintResultBuilder<T> element(T element) {
        this.element = element;
        return this;
    }

    public PrintResultBuilder<T> executionTime(long executionTime) {
        this.executionTime = executionTime;
        return this;
    }

    public String build() {
        Map<String, Object> fields = new LinkedHashMap<>();
        putIfNotNull(fields, "test", "{" + ((double)testNumber/testAmount) * 100 + " " + collection + " " + method + "}");
//        putIfNotNull(fields, "method", method);
//        putIfNotNull(fields, "collection", collection);
        putIfNotNull(fields, "index", index);
        putIfNotNull(fields, "element", element);
        putIfNotNull(fields, "execution time", executionTime);
        return new StringBuilder()
            .append(ConcurrencyUtils.threadName())
            .append(
                StringUtils
                    .joinWith(", ", getStrings(fields))
                    .replaceAll("[\\[\\]]", "")
            ).toString();
    }

    private List<String> getStrings(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        return entries.stream().map(entry -> entry.getKey() + ": " + entry.getValue()).collect(Collectors.toList());
    }

    private void putIfNotNull(Map<String, Object> fields, String key, Object value) {
        if (value != null) {
            fields.put(key, value);
        }
    }
}