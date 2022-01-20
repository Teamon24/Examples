package patterns.basic.other_source.behavioral.chain_of_responsibility;

public class DispenseUtils {

    public static void dispense(Dispenser chain,
                                Currency currency,
                                Integer nominal)
    {
        Integer remainder = currency.getRemainder();
        if(remainder >= nominal) {
            int amount = remainder / nominal;
            currency.put(nominal, amount);

            int nextRemainder = remainder % nominal;
            currency.setRemainder(nextRemainder);

            if(nextRemainder != 0) chain.dispense(currency);
        }else{
            chain.dispense(currency);
        }
    }
}
