package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
class CartesianTest {
    static final List<List> expectedProduct = List.of(
        List.of(1, 4, 7), List.of(1, 4, 8), List.of(1, 4, 9),
        List.of(1, 5, 7), List.of(1, 5, 8), List.of(1, 5, 9),
        List.of(1, 6, 7), List.of(1, 6, 8), List.of(1, 6, 9),
        List.of(2, 4, 7), List.of(2, 4, 8), List.of(2, 4, 9),
        List.of(2, 5, 7), List.of(2, 5, 8), List.of(2, 5, 9),
        List.of(2, 6, 7), List.of(2, 6, 8), List.of(2, 6, 9),
        List.of(3, 4, 7), List.of(3, 4, 8), List.of(3, 4, 9),
        List.of(3, 5, 7), List.of(3, 5, 8), List.of(3, 5, 9),
        List.of(3, 6, 7), List.of(3, 6, 8), List.of(3, 6, 9)
        );


    @Test
    void cartesianProduct() {
        List<Object> list1 = Arrays.asList(1, 2, 3);
        List<Object> list2 = Arrays.asList(4, 5, 6);
        List<Object> list3 = Arrays.asList(7, 8, 9);

        List<List> actualProduct = Cartesian.product(list1, list2, list3);

        Assertions.assertEquals(expectedProduct.size(), actualProduct.size());
        for (int i = 0; i < expectedProduct.size(); i++) {
            List expectedSublist = expectedProduct.get(i);
            List actualSublist = actualProduct.get(i);
            Assertions.assertEquals(expectedSublist.size(), actualSublist.size());
            for (int j = 0; j < expectedSublist.size(); j++) {
                Object expected = expectedSublist.get(j);
                Object actual = actualSublist.get(j);
                Assertions.assertEquals(expected, actual);
            }
        }
    }
}