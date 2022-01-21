package core.concurrency.thread.ex2;

import core.concurrency.ConcurrencyUtils;
import core.utils.IndentUtils;

import static core.utils.IndentUtils.addIndent;

public class SyncObject {
    public static final String SYNC_1 = "SYNC-1";
    public static final String SYNC_2 = "SYNC-2";
    public static final String NOT_SYNC = "NOT SYNC";

    public static final String[] names = {
        SYNC_1,
        SYNC_2,
        NOT_SYNC
    };


    public synchronized void sync1(MethodThread thread) {
        messageIn("SYNC1", thread);
        ConcurrencyUtils.sleep(thread.jobImitationTime);
        messageOut("SYNC1", thread);
    }

    public synchronized void sync2(MethodThread thread) {
        messageIn("SYNC2", thread);
        ConcurrencyUtils.sleep(thread.jobImitationTime);
        messageOut("SYNC2", thread);
    }

    public void notSync(MethodThread thread) {
        messageIn("NOT_SYNC", thread);
        ConcurrencyUtils.sleep(thread.jobImitationTime);
        messageOut("NOT_SYNC", thread);
    }

    private static void messageIn(
        final String methodName,
        final MethodThread thread
    ) {
        System.out.printf("#%s: %s invokes and waits for %s seconds\n",
            IndentUtils.addIndent(methodName, names),
            getThreadName(thread),
            thread.jobImitationTime / 1000
        );
    }

    private static void messageOut(
        final String methodName,
        final MethodThread thread)
    {
        System.out.printf("#%s: %s is out\n",
            IndentUtils.addIndent(methodName, names),
            getThreadName(thread));
    }


    private static String getThreadName(MethodThread thread) {
        return ConcurrencyUtils.threadName("[TH]#%s", addIndent(thread.id.toString(), 3));
    }



}
