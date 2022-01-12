package core.collection.tree;

import lombok.Getter;

@Getter
public class Tree<T extends Comparable<? super T>> {
    protected Node<T> root;
    protected int size;
}
