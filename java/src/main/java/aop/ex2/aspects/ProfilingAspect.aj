package aop.ex2.aspects;

import static utils.PrintUtils.println;

public aspect ProfilingAspect {
    pointcut publicOperation() : execution(@aop.ex2.aspects.Profiled * *.*(..));

    Object around() : publicOperation() {
        long start = System.nanoTime();
        Object ret = proceed();
        long end = System.nanoTime();
        println(thisJoinPointStaticPart.getSignature()
            + " took " + (end-start) + " nanoseconds");
        return ret;
    }
}