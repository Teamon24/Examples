package patterns.basic.other_source.behavioral.strategy;

import static utils.PrintUtils.println;

public class StrategyPatternDemo {
   public static void main(String[] args) {
      Context context = new Context(new OperationAdd());
      println("10 + 5 = " + context.executeStrategy(10, 5));

      context = new Context(new OperationSubstract());		
      println("10 - 5 = " + context.executeStrategy(10, 5));

      context = new Context(new OperationMultiply());		
      println("10 * 5 = " + context.executeStrategy(10, 5));
   }
}