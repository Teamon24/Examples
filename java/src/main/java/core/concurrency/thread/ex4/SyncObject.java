package core.concurrency.thread.ex4;

import utils.ConcurrencyUtils;
import core.concurrency.thread.ex4.aspect.LogInvocation;

public class SyncObject {
    public static final String SYNC_1 = "SYNC-1";
    public static final String SYNC_2 = "SYNC-2";
    public static final String NOT_SYNC = "NOT SYNC";

    public static final String[] names = {
        SYNC_1,
        SYNC_2,
        NOT_SYNC
    };

    @LogInvocation(methodName = SYNC_1)
    public synchronized void sync1(MethodThread thread) {
        ConcurrencyUtils.sleep(thread.jobImitationTime);
    }

    @LogInvocation(methodName = SYNC_2)
    public synchronized void sync2(MethodThread thread) {
        ConcurrencyUtils.sleep(thread.jobImitationTime);
    }

    @LogInvocation(methodName = NOT_SYNC)
    public void notSync(MethodThread thread) {
        ConcurrencyUtils.sleep(thread.jobImitationTime);
    }
}
