package architecture.patterns.basic.other_source.behavioral.mediator;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class Client {
    public static void main(String[] args) {

        AppMediator mediator = new AppMediator();
        AppMediator mobileMediator = new AppMediator();

        Colleague desktop1 = new ConcreteColleague("Desktop 1",mediator);
        Colleague desktop2 = new ConcreteColleague("Desktop 2",mediator);
        Colleague desktop3 = new ConcreteColleague("Desktop 3",mediator);

        Colleague mobile1  = new MobileColleague("Mobile 1", mobileMediator);
        Colleague mobile2  = new MobileColleague("Mobile 2", mobileMediator);
        Colleague mobile3  = new MobileColleague("Mobile 3", mobileMediator);

        mediator.addColleague(desktop1);
        mediator.addColleague(desktop2);
        mediator.addColleague(desktop3);

        mobileMediator.addColleague(mobile1);
        mobileMediator.addColleague(mobile2);
        mobileMediator.addColleague(mobile3);

        desktop1.send("Hello to every one");
        desktop2.send("Hello, desktop ex1");
        desktop3.send("Hello, desktop ex1");

        mobile1.send("Hello, desktop ex1");
        mobile2.send("Hello, desktop ex1");
        mobile3.send("Hello, desktop ex1");
    }
}