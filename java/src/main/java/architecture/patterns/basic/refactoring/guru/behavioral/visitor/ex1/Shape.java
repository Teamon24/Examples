package architecture.patterns.basic.refactoring.guru.behavioral.visitor.ex1;

public interface Shape {
    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
}