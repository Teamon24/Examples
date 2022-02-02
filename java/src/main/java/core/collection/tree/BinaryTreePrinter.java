package core.collection.tree;

import utils.PrintUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryTreePrinter {

    public static <T extends Comparable<? super T>> void print(BinaryNode<T> root) {
        int maxLevel = BinaryTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<? super T>> void printNodeInternal(List<BinaryNode<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BinaryTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BinaryTreePrinter.printWhitespaces(firstSpaces);

        List<BinaryNode<T>> newNodes = new ArrayList<>();
        for (BinaryNode<T> node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BinaryTreePrinter.printWhitespaces(betweenSpaces);
        }
        PrintUtils.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BinaryTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BinaryTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                printSlash(nodes.get(j).getLeft(), "/");
                BinaryTreePrinter.printWhitespaces(i + i - 1);
                printSlash(nodes.get(j).getRight(), "\\");

                BinaryTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            PrintUtils.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<? super T>> int maxLevel(BinaryNode<T> node) {
        if (node == null)
            return 0;

        return Math.max(BinaryTreePrinter.maxLevel(node.getLeft()), BinaryTreePrinter.maxLevel(node.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

    private static <T extends Comparable<? super T>> void printSlash(BinaryNode<T> nodes, String s) {
        if (nodes != null)
            System.out.print(s);
        else
            BinaryTreePrinter.printWhitespaces(1);
    }

}