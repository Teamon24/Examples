package patterns.basic.other_source.creational.factory.abstrct;

import static utils.PrintUtils.println;

public class RoundedRectangle implements Shape {
   @Override
   public void draw() {
      println("Inside RoundedRectangle::draw() method.");
   }
}