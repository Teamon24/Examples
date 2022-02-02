package patterns.basic.other_source.creational.factory.abstrct;

import static utils.PrintUtils.println;

public class Square implements Shape {

   @Override
   public void draw() {
      println("Inside Square::draw() method.");
   }
}