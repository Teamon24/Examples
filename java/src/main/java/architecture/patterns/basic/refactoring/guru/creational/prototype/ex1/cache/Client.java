package architecture.patterns.basic.refactoring.guru.creational.prototype.ex1.cache;

import architecture.patterns.basic.refactoring.guru.creational.prototype.ex1.Shape;

import static java.lang.System.out;

public class Client {
    public static void main(String[] args) {
        BundledShapeCache cache = new BundledShapeCache();

        final Shape shape1 = cache.get(BundledShapeCache.BIG_GREEN_CIRCLE);
        final Shape shape2 = cache.get(BundledShapeCache.MEDIUM_BLUE_RECTANGLE);
        final Shape shape3 = cache.get(BundledShapeCache.MEDIUM_BLUE_RECTANGLE);

        if (shape1 != shape2 && !shape1.equals(shape2)) {
            out.println("Big green circle != Medium blue rectangle (yay!)");
        } else {
            out.println("Big green circle == Medium blue rectangle (booo!)");
        }

        if (shape2 != shape3) {
            out.println("Medium blue rectangles are two different objects (yay!)");
            checkEquality(shape2, shape3);
        } else {
            out.println("Rectangle objects are the same (booo!)");
        }
    }

    private static void checkEquality(final Shape shape2, final Shape shape3) {
        if (shape2.equals(shape3)) {
            out.println("And they are identical (yay!)");
        } else {
            out.println("But they are not identical (booo!)");
        }
    }
}