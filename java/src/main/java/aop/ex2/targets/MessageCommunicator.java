package aop.ex2.targets;

import aop.ex2.aspects.Profiled;
import aop.ex2.aspects.Secured;

public class MessageCommunicator {

    protected static final String MESSAGE_DELIVERED = "Message delivered: ";

    @Secured
    @Profiled
    public void deliver(String message) {
        System.out.println(MESSAGE_DELIVERED + message);
    }

    @Secured
    @Profiled
    public void deliver(String person, String message) {
        System.out.println(MESSAGE_DELIVERED + person + ", " + message);
    }

}