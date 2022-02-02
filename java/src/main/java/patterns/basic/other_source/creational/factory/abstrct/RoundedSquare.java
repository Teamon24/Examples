package patterns.basic.other_source.creational.factory.abstrct;

import static utils.PrintUtils.println;

public class RoundedSquare implements Shape {
   @Override
   public void draw() {
      println("Inside RoundedSquare::draw() method.");
   }
}