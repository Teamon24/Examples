package architecture.patterns.basic.other_source.creational.factory.abstrct;

public class Client {
   public static void main(String[] args) {
      boolean rounded = true;
      boolean notRounded = !rounded;
      AbstractFactory shapeFactory = FactoryProducer.getFactory(notRounded);
      Shape shape1 = shapeFactory.getShape("RECTANGLE");
      shape1.draw();
      Shape shape2 = shapeFactory.getShape("SQUARE");
      shape2.draw();

      AbstractFactory shapeFactory1 = FactoryProducer.getFactory(rounded);
      Shape shape3 = shapeFactory1.getShape("RECTANGLE");
      shape3.draw();
      Shape shape4 = shapeFactory1.getShape("SQUARE");
      shape4.draw();
   }
}