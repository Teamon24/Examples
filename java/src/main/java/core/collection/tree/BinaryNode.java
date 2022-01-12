package core.collection.tree;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class BinaryNode<T extends Comparable<? super T>> extends Node<T> {

    private BinaryNode<T> parent;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    private int balanceFactor;

    public final static Node MOCK = new Node<>();

    public BinaryNode(T value) {
        this.value = value;
        initChildren();
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }

    public void setLeft(BinaryNode<T> left) {
        if (super.children.isEmpty()) initChildren();
        if (left != null) {
            super.children.set(0, left);
            this.left = left;
        }
    }

    public void setRight(BinaryNode<T> right) {
        if (super.children.isEmpty()) initChildren();
        if (right != null) {
            super.children.set(1, right);
            this.right = right;
        }
    }

    public boolean moreThan(BinaryNode<T> node) {
        return this.value.compareTo(node.value) > 0;
    }
    public boolean lessThan(BinaryNode<T> node) {
        return this.value.compareTo(node.value) < 0;
    }
    public boolean equalsTo(BinaryNode<T> node) {
        return this.value.compareTo(node.value) == 0;
    }

    @Override
    public List<Node<T>> getChildren() {
        if (super.children.isEmpty()) {
            if (Objects.nonNull(this.left)) super.children.add(this.left);
            if (Objects.nonNull(this.right)) super.children.add(this.right);
        }
        super.children.remove(MOCK);
        return super.children;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    private void initChildren() {
        super.children = new ArrayList<>(2);
        super.children.add(MOCK);
        super.children.add(MOCK);
    }

    @Override
    public String toString() {
        return (left == null ? "-" : left.value) + "<- " + value + " ->" + (right == null ? "-" : right.value);
    }
}
