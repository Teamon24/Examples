package patterns.basic.refactoring.guru.creational.prototype.ex1.cache;

import patterns.basic.refactoring.guru.creational.prototype.ex1.Shape;
import utils.PrintUtils;

import static utils.PrintUtils.*;

public class Client {
    public static void main(String[] args) {
        BundledShapeCache cache = new BundledShapeCache();

        final Shape shape1 = cache.get(BundledShapeCache.BIG_GREEN_CIRCLE);
        final Shape shape2 = cache.get(BundledShapeCache.MEDIUM_BLUE_RECTANGLE);
        final Shape shape3 = cache.get(BundledShapeCache.MEDIUM_BLUE_RECTANGLE);

        if (shape1 != shape2 && !shape1.equals(shape2)) {
            println("Big green circle != Medium blue rectangle (yay!)");
        } else {
            println("Big green circle == Medium blue rectangle (booo!)");
        }

        if (shape2 != shape3) {
            println("Medium blue rectangles are two different objects (yay!)");
            checkEquality(shape2, shape3);
        } else {
            println("Rectangle objects are the same (booo!)");
        }
    }

    private static void checkEquality(final Shape shape2, final Shape shape3) {
        if (shape2.equals(shape3)) {
            println("And they are identical (yay!)");
        } else {
            println("But they are not identical (booo!)");
        }
    }
}