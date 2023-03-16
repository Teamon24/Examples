package patterns.basic.other_source.behavioral.mediator;

import java.util.ArrayList;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class AppMediator implements Mediator {

    private ArrayList<Colleague> colleagues;

    public AppMediator() {
        colleagues = new ArrayList<>();
    }

    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }

    public void send(String message, Colleague originator) {
        originator.receive(message);
        //let all org.home.other screens know that this screen has changed
        for(Colleague colleague: colleagues) {
            //don't tell ourselves
            if(colleague != originator) {
                colleague.receive(originator.getName() + " received " + "\"" +message+ "\"");
            }
        }
    }
}
