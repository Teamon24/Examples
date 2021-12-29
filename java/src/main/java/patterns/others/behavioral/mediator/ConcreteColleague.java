package patterns.others.behavioral.mediator;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class ConcreteColleague extends Colleague {

    public ConcreteColleague(String name, Mediator m) {
        super(name, m);
    }

    public void receive(String message) {
        System.out.printf("%s received: %s\n", this.getName(), message);
    }
}
