package patterns.refactoring.guru.creational.prototype.ex1.cache;

import patterns.refactoring.guru.creational.prototype.ex1.Circle;
import patterns.refactoring.guru.creational.prototype.ex1.Rectangle;
import patterns.refactoring.guru.creational.prototype.ex1.Shape;

import java.util.HashMap;
import java.util.Map;

public class BundledShapeCache {

    public static final String BIG_GREEN_CIRCLE = "Big green circle";
    public static final String MEDIUM_BLUE_RECTANGLE = "Medium blue rectangle";
    private Map<String, Shape> cache = new HashMap<>();

    public BundledShapeCache() {
        Circle circle = new Circle();
        circle.x = 5;
        circle.y = 7;
        circle.radius = 45;
        circle.color = "Green";

        Rectangle rectangle = new Rectangle();
        rectangle.x = 6;
        rectangle.y = 9;
        rectangle.width = 8;
        rectangle.height = 10;
        rectangle.color = "Blue";

        cache.put(BIG_GREEN_CIRCLE, circle);
        cache.put(MEDIUM_BLUE_RECTANGLE, rectangle);
    }

    public Shape put(String key, Shape shape) {
        cache.put(key, shape);
        return shape;
    }

    public Shape get(String key) {
        return cache.get(key).clone();
    }
}