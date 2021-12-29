package collection;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 12.01.2017.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class TestLinkedHashSet {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>();

        set.add("London");
        set.add("Paris");
        set.add("New York");
        set.add("San Francisco");
        set.add("Berling");
        set.add("New York");
        System.out.println(set);

        for (Object element : set)
            System.out.print(element.toString() + " ");

    }
}
