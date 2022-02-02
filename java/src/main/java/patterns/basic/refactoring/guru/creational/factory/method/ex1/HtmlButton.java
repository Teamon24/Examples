package patterns.basic.refactoring.guru.creational.factory.method.ex1;

import utils.PrintUtils;

import static utils.PrintUtils.*;

/**
 * Реализация HTML кнопок.
 */
public class HtmlButton implements Button {

    public void render() {
        println("<button>Test Button</button>");
        onClick();
    }

    public void onClick() {
        println("Click! Button says - 'Hello World!'");
    }
}