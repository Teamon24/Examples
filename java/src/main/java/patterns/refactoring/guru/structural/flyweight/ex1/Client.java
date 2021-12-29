package patterns.refactoring.guru.structural.flyweight.ex1;

import java.awt.*;

public class Client {
    static int CANVAS_SIZE = 500;
    static int TREES_TO_DRAW = 1000000;
    static int TREE_TYPES = 2;

    public static void main(String[] args) {
        Forest forest = new Forest();
        for (int i = 0; i < Math.floor(TREES_TO_DRAW / TREE_TYPES); i++) {
            forest.plantTree(
                coordinates(CANVAS_SIZE),
                coordinates(CANVAS_SIZE), "Summer Oak", Color.GREEN, "Oak texture stub");
            forest.plantTree(
                coordinates(CANVAS_SIZE),
                coordinates(CANVAS_SIZE), "Autumn Oak", Color.ORANGE, "Autumn Oak texture stub");
        }
        forest.setSize(CANVAS_SIZE, CANVAS_SIZE);
        forest.setVisible(true);

        System.out.println(TREES_TO_DRAW + " trees drawn");
        System.out.println("---------------------------");
        System.out.println("Memory usage:");
        System.out.printf("Tree      size (8 bytes)   * %s +\n", TREES_TO_DRAW);
        System.out.printf("TreeTypes size (~30 bytes) * %s\n", TREE_TYPES);
        System.out.println("---------------------------");
        System.out.printf("Total: %sMB\n", ((TREES_TO_DRAW * 8 + TREE_TYPES * 30) / 1024 / 1024));
        System.out.printf("(instead of %sMB)\n", ((TREES_TO_DRAW * 38) / 1024 / 1024));
    }

    private static int coordinates(int canvasSize) {
        return random(0, canvasSize);
    }

    private static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}