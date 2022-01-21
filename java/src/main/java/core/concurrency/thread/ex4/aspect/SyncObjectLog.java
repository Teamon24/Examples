package core.concurrency.thread.ex4.aspect;

import core.concurrency.ConcurrencyUtils;
import core.concurrency.thread.ex4.MethodThread;
import core.concurrency.thread.ex4.SyncObject;
import core.utils.IndentUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import static core.utils.IndentUtils.addIndent;

@Aspect
public class SyncObjectLog {

    public static final String[] names = SyncObject.names;

    @Pointcut("execution(* core.concurrency.thread.ex4.SyncObject.*(..))")
    public void methodCall() {}

    @Before("methodCall()")
    public void beforeAdvice(JoinPoint call) {
        String methodName = getMethodName(call);
        MethodThread thread = getThread(call);
        messageIn(methodName, thread);
    }

    @After("methodCall()")
    public void afterAdvice(JoinPoint call) {
        String methodName = getMethodName(call);
        MethodThread thread = getThread(call);
        messageOut(methodName, thread);
    }

    private MethodThread getThread(JoinPoint call) {
        return (MethodThread) call.getArgs()[1];
    }

    private String getMethodName(JoinPoint call) {
        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();
        LogInvocation myAnnotation = method.getAnnotation(LogInvocation.class);
        return myAnnotation.methodName();
    }

    private void messageIn(
        final String methodName,
        final MethodThread thread
    ) {
        System.out.printf("#%s: %s invokes and waits for %s seconds\n",
            IndentUtils.addIndent(methodName, names),
            getThreadName(thread),
            thread.jobImitationTime / 1000
        );
    }

    private void messageOut(
        final String methodName,
        final MethodThread thread)
    {
        System.out.printf("#%s: %s is out\n",
            IndentUtils.addIndent(methodName, names),
            getThreadName(thread));
    }

    private String getThreadName(MethodThread thread) {
        return ConcurrencyUtils.threadName("[TH]#%s", addIndent(thread.id.toString(), 3));
    }
}