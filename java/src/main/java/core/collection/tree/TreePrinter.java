package core.collection.tree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TreePrinter {
    public static <T extends Comparable<? super T>> void print(Node<T> root, StringBuilder buffer) {
        print(root, buffer, "", "");
    }

    public static <T extends Comparable<? super T>> void print(Node<T> root, StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(root.getValue()).append(":").append(root.height);
        buffer.append('\n');
        List<Node<T>> children = root.getChildren();
        children.sort(Comparator.reverseOrder());
        for (Iterator<Node<T>> it = children.iterator(); it.hasNext();) {
            Node<T> next = it.next();
            if (it.hasNext()) {
                print(next, buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                print(next, buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
