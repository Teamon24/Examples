package patterns.others.behavioral.chain_of_responsibility;

import java.util.Arrays;

public class Client {
    public static final ATMDispenseChain ATM_DISPENSER =
        new ATMDispenseChain();

    public static void main(String[] args) {
        Integer minimalNominal = ATM_DISPENSER.getMinimalNominal();

        Arrays
            .asList(5115, 410, 232, 11)
            .forEach(requestedMoney -> dispense(minimalNominal, requestedMoney));
    }

    private static void dispense(final Integer minimalNominal,
                                 final Integer requestedMoney)
    {
        printAmount(requestedMoney);
        if (requestedMoney % minimalNominal != 0) {
            printErrorMessage();
        } else {
            ATM_DISPENSER
                .dispense(requestedMoney)
                .forEach(Client::printDispensed);
        }
    }

    private static void printDispensed(final Integer nominal, final Integer amount) {
        String message = String.format("nominal: %s$, amounts: %s", nominal, amount);
        System.out.println(message);
    }

    private static void printErrorMessage() {
        System.out.println("Can't be dispensed");
    }

    private static void printAmount(Integer amount) {
        System.out.println("-------------------------");
        System.out.println(String.format("Amount: %s$", amount));
    }
}
