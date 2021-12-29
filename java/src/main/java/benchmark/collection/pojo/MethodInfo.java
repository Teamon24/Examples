package benchmark.collection.pojo;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MethodInfo {

    private final String collectionClass;
    private final MethodType methodType;
    private static List<MethodInfo> methodInfos = new ArrayList<>();

    static {
        Set collectionClasses = Set.of(
            ArrayList.class, LinkedList.class, TreeList.class,
            HashSet.class, TreeSet.class, LinkedHashSet.class);

        methodInfos.addAll(
            cartesian(
                collectionClasses,
                MethodType.types(),
                (Class collectionClass, MethodType type) ->
                    new MethodInfo(collectionClass.getSimpleName(), type)
            ));

    }

    private static <Type1, Type2, Type3> List<Type3> cartesian(
        final Set<Type1> firsts,
        final Set<Type2> seconds,
        final BiFunction<Type1, Type2, Type3> create)
    {
        Set<List<Object>> set = Sets.cartesianProduct(firsts, seconds);

        return set.stream()
            .map(it -> create.apply((Type1) it.get(0), (Type2) it.get(1)))
            .collect(Collectors.toList());
    }

    public MethodInfo(final String collectionClass,
                      final MethodType methodType)
    {

        this.collectionClass = collectionClass;
        this.methodType = methodType;
    }

    public String getCollectionClass() {
        return collectionClass;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public static MethodInfo get(final String collectionClass,
                                 final MethodType methodType)
    {
        return methodInfos.stream()
            .filter(it ->
                it.collectionClass.equals(collectionClass) &&
                it.methodType == methodType)
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    String.format(
                        "There is no methodInfo for collection \"%s\" and type \"%s\"",
                        collectionClass, methodType)));
    }
}
