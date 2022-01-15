package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Cartesian {
    public static <T> List<List<T>> product(Collection<T>... cols) {
        if (cols == null) return Collections.emptyList();
        List<List<T>> cp = Collections.singletonList(Collections.emptyList());

        for (Collection<T> col : cols) {
            // non-null and non-empty collections
            if (col == null || col.size() == 0) continue;
            // intermediate result for next iteration
            List<List<T>> next = new ArrayList<>();
            // rows of current intermediate result
            for (List<T> row : cp) {
                // elements of current list
                for (T el : col) {
                    // new row for next intermediate result
                    List<T> nRow = new ArrayList<>(row);
                    nRow.add(el);
                    next.add(nRow);
                }
            }
            // pass to next iteration
            cp = next;
        }
        // Cartesian product, final result
        return cp;
    }
}
