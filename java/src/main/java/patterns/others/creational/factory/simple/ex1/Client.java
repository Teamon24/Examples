package patterns.others.creational.factory.simple.ex1;

import java.util.stream.Stream;

import static patterns.others.creational.factory.simple.ex1.ShapeType.CIRCLE;
import static patterns.others.creational.factory.simple.ex1.ShapeType.RECTANGLE;
import static patterns.others.creational.factory.simple.ex1.ShapeType.SQUARE;

public class Client {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.getShape(CIRCLE);
        Shape rectangle = shapeFactory.getShape(RECTANGLE);
        Shape square = shapeFactory.getShape(SQUARE);

        Stream.of(circle, rectangle, square).forEach(Shape::draw);
    }
}
