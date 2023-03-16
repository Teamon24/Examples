package patterns.basic.other_source.creational.factory.abstrct;

import static java.lang.System.out;

public class Rectangle implements Shape {
   @Override
   public void draw() {
      out.println("Inside Rectangle::draw() method.");
   }
}