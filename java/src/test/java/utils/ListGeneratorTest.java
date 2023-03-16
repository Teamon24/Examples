package utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ListGeneratorTest {

    @Test
    void create() {

        List<Integer> range = IntStream.range(1, 4).boxed().collect(Collectors.toList());
        List<String> strings = range.stream().map(Objects::toString).collect(Collectors.toList());

        List<A> actualValues = ListGenerator.create(
            list -> A.of((Integer) list.get(0), (String) list.get(1), (B) list.get(2)),
            range,
            strings,
            ListGenerator.create(
                list -> B.of((Integer) list.get(0), (String) list.get(1)),
                range,
                strings)
        );
        Assertions.assertEquals(81, actualValues.size());
        expectedValues.forEach(expectedValue ->
            Assertions.assertTrue(
                actualValues.stream().filter(actualValue -> actualValue.equals(expectedValue)).count() == 1
        ));
    }


    List<A> expectedValues = new ArrayList<>() {{

        add(A.of(1, "1", B.of(1, "1")));
        add(A.of(3, "1", B.of(1, "3")));
        add(A.of(2, "3", B.of(3, "1")));
        add(A.of(1, "1", B.of(2, "1")));
        add(A.of(2, "1", B.of(2, "3")));
        add(A.of(3, "3", B.of(1, "1")));
        add(A.of(1, "2", B.of(1, "2")));
        add(A.of(1, "2", B.of(2, "1")));
        add(A.of(1, "3", B.of(2, "3")));
        add(A.of(1, "2", B.of(2, "3")));
        add(A.of(2, "1", B.of(1, "2")));
        add(A.of(3, "2", B.of(2, "1")));
        add(A.of(2, "1", B.of(1, "1")));
        add(A.of(2, "3", B.of(2, "1")));
        add(A.of(1, "1", B.of(2, "2")));
        add(A.of(3, "2", B.of(3, "3")));
        add(A.of(1, "3", B.of(2, "1")));
        add(A.of(3, "1", B.of(2, "3")));
        add(A.of(1, "1", B.of(3, "3")));
        add(A.of(2, "3", B.of(2, "3")));
        add(A.of(3, "1", B.of(3, "1")));
        add(A.of(3, "2", B.of(1, "1")));
        add(A.of(1, "3", B.of(3, "3")));
        add(A.of(1, "2", B.of(3, "1")));
        add(A.of(3, "1", B.of(1, "1")));
        add(A.of(2, "2", B.of(3, "2")));
        add(A.of(2, "1", B.of(3, "1")));
        add(A.of(1, "2", B.of(2, "2")));
        add(A.of(3, "3", B.of(2, "3")));
        add(A.of(2, "2", B.of(2, "3")));
        add(A.of(1, "1", B.of(2, "3")));
        add(A.of(3, "2", B.of(2, "2")));
        add(A.of(2, "2", B.of(3, "1")));
        add(A.of(1, "1", B.of(3, "2")));
        add(A.of(1, "1", B.of(1, "2")));
        add(A.of(2, "2", B.of(2, "1")));
        add(A.of(2, "3", B.of(3, "2")));
        add(A.of(2, "3", B.of(2, "2")));
        add(A.of(2, "2", B.of(3, "3")));
        add(A.of(3, "2", B.of(2, "3")));
        add(A.of(3, "1", B.of(3, "2")));
        add(A.of(3, "3", B.of(2, "1")));
        add(A.of(1, "2", B.of(3, "3")));
        add(A.of(2, "3", B.of(1, "1")));
        add(A.of(2, "2", B.of(2, "2")));
        add(A.of(3, "1", B.of(1, "2")));
        add(A.of(1, "3", B.of(1, "3")));
        add(A.of(3, "2", B.of(3, "1")));
        add(A.of(3, "3", B.of(1, "3")));
        add(A.of(3, "1", B.of(2, "2")));
        add(A.of(1, "2", B.of(1, "1")));
        add(A.of(1, "2", B.of(3, "2")));
        add(A.of(2, "2", B.of(1, "2")));
        add(A.of(2, "2", B.of(1, "3")));
        add(A.of(3, "2", B.of(1, "2")));
        add(A.of(1, "3", B.of(2, "2")));
        add(A.of(3, "3", B.of(3, "2")));
        add(A.of(1, "2", B.of(1, "3")));
        add(A.of(3, "1", B.of(3, "3")));
        add(A.of(3, "2", B.of(3, "2")));
        add(A.of(1, "1", B.of(3, "1")));
        add(A.of(2, "3", B.of(1, "3")));
        add(A.of(3, "3", B.of(1, "2")));
        add(A.of(2, "1", B.of(3, "2")));
        add(A.of(1, "3", B.of(1, "2")));
        add(A.of(2, "1", B.of(2, "1")));
        add(A.of(1, "3", B.of(3, "2")));
        add(A.of(2, "3", B.of(1, "2")));
        add(A.of(2, "2", B.of(1, "1")));
        add(A.of(3, "1", B.of(2, "1")));
        add(A.of(2, "3", B.of(3, "3")));
        add(A.of(3, "3", B.of(3, "3")));
        add(A.of(3, "2", B.of(1, "3")));
        add(A.of(3, "3", B.of(3, "1")));
        add(A.of(1, "3", B.of(1, "1")));
        add(A.of(2, "1", B.of(2, "2")));
        add(A.of(3, "3", B.of(2, "2")));
        add(A.of(1, "1", B.of(1, "3")));
        add(A.of(2, "1", B.of(3, "3")));
        add(A.of(1, "3", B.of(3, "1")));
        add(A.of(2, "1", B.of(1, "3")));
    }};
}

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class A {

    Integer a;
    String s;
    B b;

    public static A of(Integer a, String s, B c) {
        return new A(a, s, c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof A)) return false;
        A a1 = (A) o;
        return Objects.equals(getA(), a1.getA()) &&
            Objects.equals(this.getS(), a1.getS()) &&
            Objects.equals(this.getB(), a1.getB());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = result * prime + getA().hashCode();
        result = result * prime + this.getS().hashCode();
        result = result * prime + this.getB().hashCode();
        return result;
    }
}

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class B {

    Integer c;
    String d;

    public static B of(Integer c, String d) {
        return new B(c, d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof B)) return false;
        B b = (B) o;
        return Objects.equals(c, b.c) && Objects.equals(d, b.d);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = result * prime + this.c.hashCode();
        result = result * prime + this.d.hashCode();
        return result;
    }
}

