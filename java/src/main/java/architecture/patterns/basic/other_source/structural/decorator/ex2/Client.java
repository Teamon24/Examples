package architecture.patterns.basic.other_source.structural.decorator.ex2;

public class Client {
   public static void main(String[] args) {
      Shape circle = new Circle();
      Shape redCircle = new RedShapeDecorator(new Circle());
      Shape redRectangle = new RedShapeDecorator(new Rectangle());

      circle.draw();
      redCircle.draw();
      redRectangle.draw();
   }
}