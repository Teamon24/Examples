package patterns.basic.other_source.structural.decorator.ex2;

import static java.lang.System.out;

public class Rectangle implements Shape {

   @Override
   public void draw() {
      out.println("Shape: Rectangle");
   }
}