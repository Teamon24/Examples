package core.concurrency.thread.ex2;

public class MethodThread extends Thread {

    public final Integer id;
    public final SyncObject syncObject;
    public final int jobImitationTime;
    private SyncMethod syncMethod;

    public MethodThread(
        final Integer id,
        final SyncObject syncObject,
        int jobImitationTime
    ) {
        this.id = id;
        this.syncObject = syncObject;
        this.jobImitationTime = jobImitationTime;
    }

    @Override
    public void run() {
        syncMethod.accept(this.syncObject, this);
    }

    public void setMethod(final SyncMethod syncMethod) {
        this.syncMethod = syncMethod;
    }
}
