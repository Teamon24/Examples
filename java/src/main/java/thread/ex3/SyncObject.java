package thread.ex3;

import thread.ex2.ThreadUtils;

public class SyncObject {
        int millis = 1500;

    public synchronized void sync1(SyncThread thread) {
        String sync1 = "SYNC1";
        messageIn(sync1, thread);
        ThreadUtils.sleep(millis);
        messageOut(thread, sync1);
    }

    public synchronized void sync2(SyncThread thread) {
        String sync2 = "SYNC2";
        messageIn(sync2, thread);
        ThreadUtils.sleep(millis);
        messageOut(thread, sync2);
    }

    public void notSync(SyncThread thread) {
        String notSync = "NOT_SYNC";
        messageIn(notSync, thread);
        ThreadUtils.sleep(millis);
        messageOut(thread, notSync);
    }

    private void messageOut(final SyncThread thread, final String notSync) {
        System.out.printf("#%s:%s is out\n", notSync, thread.id);
    }

    private void messageIn(final String methodName, final SyncThread thread) {
        System.out.printf("#%s:%s invokes and waits for %s seconds\n", methodName, thread.id, millis/1000);
    }



}
