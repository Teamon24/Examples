package patterns.basic.other_source.creational.factory.simple.ex1;

import java.util.stream.Stream;

public class Client {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.getShape(ShapeType.CIRCLE);
        Shape rectangle = shapeFactory.getShape(ShapeType.RECTANGLE);
        Shape square = shapeFactory.getShape(ShapeType.SQUARE);

        Stream.of(circle, rectangle, square).forEach(Shape::draw);
    }
}
