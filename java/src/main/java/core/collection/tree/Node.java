package core.collection.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Node<T extends Comparable<? super T>> implements Comparable<Node<T>>{
    @Setter
    protected int height;

    @Setter
    protected T value;

    protected List<Node<T>> children = new ArrayList<>();

    @Override
    public int compareTo(Node<T> o) {
        return this.value.compareTo(o.value);
    }
}
