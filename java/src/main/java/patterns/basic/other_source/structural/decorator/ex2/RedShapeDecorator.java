package patterns.basic.other_source.structural.decorator.ex2;

import static utils.PrintUtils.println;

public class RedShapeDecorator extends ShapeDecorator {

   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);		
   }

   @Override
   public void draw() {
      decoratedShape.draw();	       
      setRedBorder(decoratedShape);
   }

   private void setRedBorder(Shape decoratedShape) {
      println("Border Color: Red");
   }
}