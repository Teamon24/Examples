package patterns.basic.other_source.creational.factory.simple.ex1;

import static utils.PrintUtils.println;

public class Rectangle implements Shape {

    @Override
    public void draw() {
        println("Inside Rectangle::draw() method.");
    }
}