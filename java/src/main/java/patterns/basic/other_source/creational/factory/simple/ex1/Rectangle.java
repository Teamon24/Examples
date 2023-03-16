package patterns.basic.other_source.creational.factory.simple.ex1;

import utils.PrintUtils;

import static java.lang.System.out;

public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}