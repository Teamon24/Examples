package patterns.basic.other_source.behavioral.mediator;

import static utils.PrintUtils.printfln;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class ConcreteColleague extends Colleague {

    public ConcreteColleague(String name, Mediator m) {
        super(name, m);
    }

    public void receive(String message) {
        printfln("%s received: %s", this.getName(), message);
    }
}
