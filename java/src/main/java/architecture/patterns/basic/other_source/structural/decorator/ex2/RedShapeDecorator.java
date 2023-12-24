package architecture.patterns.basic.other_source.structural.decorator.ex2;

import static java.lang.System.out;

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
      out.println("Border Color: Red");
   }
}