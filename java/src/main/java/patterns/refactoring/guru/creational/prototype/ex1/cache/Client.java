package patterns.refactoring.guru.creational.prototype.ex1.cache;

import patterns.refactoring.guru.creational.prototype.ex1.Shape;

import static patterns.refactoring.guru.creational.prototype.ex1.cache.BundledShapeCache.*;

public class Client {
    public static void main(String[] args) {
        BundledShapeCache cache = new BundledShapeCache();

        final Shape shape1 = cache.get(BIG_GREEN_CIRCLE);
        final Shape shape2 = cache.get(MEDIUM_BLUE_RECTANGLE);
        final Shape shape3 = cache.get(MEDIUM_BLUE_RECTANGLE);

        if (shape1 != shape2 && !shape1.equals(shape2)) {
            System.out.println("Big green circle != Medium blue rectangle (yay!)");
        } else {
            System.out.println("Big green circle == Medium blue rectangle (booo!)");
        }

        if (shape2 != shape3) {
            System.out.println("Medium blue rectangles are two different objects (yay!)");
            checkEquality(shape2, shape3);
        } else {
            System.out.println("Rectangle objects are the same (booo!)");
        }
    }

    private static void checkEquality(final Shape shape2, final Shape shape3) {
        if (shape2.equals(shape3)) {
            System.out.println("And they are identical (yay!)");
        } else {
            System.out.println("But they are not identical (booo!)");
        }
    }
}