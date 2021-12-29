package benchmark.collection.pojo;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MethodType {
    GET("GET"),
    SET("SET"),
    REMOVE("REMOVE"),
    REMOVE_BY_INDEX("REMOVE_BY_INDEX"),
    REMOVE_ELEMENT("REMOVE_ELEMENT"),
    ADD("ADD"),
    ADD_BY_INDEX("ADD"),
    ADD_ELEMENT("ADD");

    private String name;

    MethodType(final String name) {
        this.name = name;
    }

    public String getValue() {
        return this.toString();
    }

    public static Set<MethodType> types() {
        return Arrays.stream(MethodType.values()).collect(Collectors.toSet());
    }
}
