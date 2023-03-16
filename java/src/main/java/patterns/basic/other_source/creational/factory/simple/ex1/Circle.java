package patterns.basic.other_source.creational.factory.simple.ex1;

import utils.PrintUtils;

import static java.lang.System.out;

public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}