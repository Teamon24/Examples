package dbms.hibernate.user_type.ex1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Salary {
    private int moneyAmount;
    private String currency;

    public Salary(Salary salary) {
        this.moneyAmount = salary.moneyAmount;
        this.currency = salary.currency;
    }
}
