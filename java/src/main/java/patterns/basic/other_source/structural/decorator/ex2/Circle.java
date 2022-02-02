package patterns.basic.other_source.structural.decorator.ex2;

import static utils.PrintUtils.println;

public class Circle implements Shape {

   @Override
   public void draw() {
      println("Shape: Circle");
   }
}