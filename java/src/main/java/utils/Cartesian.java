package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Cartesian {
    public static List<List> product(Collection... cols) {
        if (cols == null) return Collections.emptyList();
        List<List> cp = Collections.singletonList(Collections.emptyList());

        for (Collection col : cols) {
            // non-null and non-empty collections
            if (col == null || col.size() == 0) continue;
            // intermediate result for next iteration
            List<List> next = new ArrayList<>();
            // rows of current intermediate result
            for (List row : cp) {
                // elements of current list
                for (Object el : col) {
                    // new row for next intermediate result
                    List nRow = new ArrayList(row);
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
