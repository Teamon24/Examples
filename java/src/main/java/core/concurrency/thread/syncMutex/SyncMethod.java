package core.concurrency.thread.syncMutex;

import java.util.function.BiConsumer;

interface SyncMethod extends BiConsumer<SyncObject, MethodThread> {}
