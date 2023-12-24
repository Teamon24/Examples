package architecture.patterns.basic.other_source.creational.factory.abstrct;

import static java.lang.System.out;

public class RoundedRectangle implements Shape {
   @Override
   public void draw() {
      out.println("Inside RoundedRectangle::draw() method.");
   }
}