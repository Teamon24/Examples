package architecture.patterns.basic.other_source.creational.factory.simple.ex1;

public class ShapeFactory {

   public Shape getShape(String shapeType){
      if(shapeType == null) {
         return null;
      }

      if(shapeType.equalsIgnoreCase(ShapeType.CIRCLE)) {
         return new Circle();
      } else if(shapeType.equalsIgnoreCase(ShapeType.RECTANGLE)) {
         return new Rectangle();
      } else if(shapeType.equalsIgnoreCase(ShapeType.SQUARE)) {
         return new Square();
      }
      
      return null;
   }
}