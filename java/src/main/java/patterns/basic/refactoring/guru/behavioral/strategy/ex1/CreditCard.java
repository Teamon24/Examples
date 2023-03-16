package patterns.basic.refactoring.guru.behavioral.strategy.ex1;

import lombok.Getter;
import lombok.Setter;

/**
 * Очень наивная реализация кредитной карты.
 */
public class CreditCard {
    @Getter
    @Setter
    private int money;
    private String number;
    private String date;
    private String cvv;

    CreditCard(String number, String date, String cvv) {
        this.money = 100_000;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
    }
}