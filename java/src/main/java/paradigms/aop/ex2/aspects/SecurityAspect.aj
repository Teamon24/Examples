package paradigms.aop.ex2.aspects;

import utils.ConcurrencyUtils;

public aspect SecurityAspect {

    pointcut secureAccess() : execution(@paradigms.aop.ex2.aspects.Secured * *.*(..));

    before() : secureAccess() {
        ConcurrencyUtils.threadPrintln("Checking and authenticating user");
    }
}
