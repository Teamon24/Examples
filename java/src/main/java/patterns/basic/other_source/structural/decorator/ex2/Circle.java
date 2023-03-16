package patterns.basic.other_source.structural.decorator.ex2;

import static java.lang.System.out;

public class Circle implements Shape {

   @Override
   public void draw() {
      out.println("Shape: Circle");
   }
}