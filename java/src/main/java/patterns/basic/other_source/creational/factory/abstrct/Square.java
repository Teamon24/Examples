package patterns.basic.other_source.creational.factory.abstrct;

import static java.lang.System.out;

public class Square implements Shape {

   @Override
   public void draw() {
      out.println("Inside Square::draw() method.");
   }
}