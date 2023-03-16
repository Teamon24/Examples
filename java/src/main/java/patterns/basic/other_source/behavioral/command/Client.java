package patterns.basic.other_source.behavioral.command;

import patterns.basic.other_source.behavioral.command.commands.DecrementCommand;
import patterns.basic.other_source.behavioral.command.commands.IncrementCommand;
import utils.PrintUtils;

import java.util.Random;

public class Client {

    public static void main(String[] args) {

        Random random           = new Random();
        Incrementer incrementer = new Incrementer();
        Counter counter         = new Counter();

        int incr = random.nextInt(1000);
        int decr = random.nextInt(1000);

        IncrementCommand increment = new IncrementCommand(incrementer);
        DecrementCommand decrement = new DecrementCommand(incrementer);

        counter.setCommand(increment);
        for (int i = 0; i < incr; i++) {
            counter.click();
        }

        counter.setCommand(decrement);
        for (int i = 0; i < decr; i++) {
            counter.click();
        }

        PrintUtils.printfln("увеличено раз: %d;\nуменьшено раз: %d;\nрезультат: %d", incr, decr, incrementer.getI());
    }
}
