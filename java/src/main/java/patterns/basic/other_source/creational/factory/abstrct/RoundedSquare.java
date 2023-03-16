package patterns.basic.other_source.creational.factory.abstrct;

import static java.lang.System.out;

public class RoundedSquare implements Shape {
   @Override
   public void draw() {
      out.println("Inside RoundedSquare::draw() method.");
   }
}