package core.concurrency.thread.ex2;

import java.util.function.BiConsumer;

public class SyncThread extends Thread {

    public final Integer id;
    public final SyncObject syncObject;
    private BiConsumer<SyncObject, SyncThread> method;

    public SyncThread(final Integer id, final SyncObject syncObject) {
        this.id = id;
        this.syncObject = syncObject;
    }

    @Override
    public void run() {
        method.accept(this.syncObject, this);
    }

    public void setMethod(final BiConsumer<SyncObject, SyncThread> method) {
        this.method = method;
    }
}
