package architecture.patterns.basic.refactoring.guru.creational.factory.method.ex1;

import static java.lang.System.out;

/**
 * Реализация HTML кнопок.
 */
public class HtmlButton implements Button {

    public void render() {
        out.println("<button>Test Button</button>");
        onClick();
    }

    public void onClick() {
        out.println("Click! Button says - 'Hello World!'");
    }
}