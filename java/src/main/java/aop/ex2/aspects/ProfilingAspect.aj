package aop.ex2.aspects;

public aspect ProfilingAspect {
    pointcut publicOperation() : execution(@aop.ex2.aspects.Profiled * *.*(..));

    Object around() : publicOperation() {
        long start = System.nanoTime();
        Object ret = proceed();
        long end = System.nanoTime();
        System.out.println(thisJoinPointStaticPart.getSignature()
            + " took " + (end-start) + " nanoseconds");
        return ret;
    }
}