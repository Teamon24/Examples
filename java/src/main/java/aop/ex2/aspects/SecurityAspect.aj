package aop.ex2.aspects;

public aspect SecurityAspect {

    pointcut secureAccess()
        : execution(* aop.ex2.targets.MessageCommunicator.deliver(..));

    before() : secureAccess() {
        System.out.println("Checking and authenticating user");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
