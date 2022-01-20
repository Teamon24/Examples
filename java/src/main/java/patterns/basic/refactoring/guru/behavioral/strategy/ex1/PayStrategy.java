package patterns.basic.refactoring.guru.behavioral.strategy.ex1;

/**
 * Общий интерфейс всех стратегий.
 */
public interface PayStrategy {
    boolean pay(int paymentAmount);
    void collectPaymentDetails();
}