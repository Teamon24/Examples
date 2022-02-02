package aop.ex2.targets;

import aop.ex2.aspects.Profiled;
import aop.ex2.aspects.Secured;

import static utils.PrintUtils.println;

public class MessageCommunicator {

    protected static final String MESSAGE_DELIVERED = "Message delivered: ";

    @Secured
    @Profiled
    public void deliver(String message) {
        println(MESSAGE_DELIVERED + message);
    }

    @Secured
    @Profiled
    public void deliver(String person, String message) {
        println(MESSAGE_DELIVERED + person + ", " + message);
    }

}