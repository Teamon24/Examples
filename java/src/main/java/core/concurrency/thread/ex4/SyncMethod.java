package core.concurrency.thread.ex4;

import java.util.function.BiConsumer;

interface SyncMethod extends BiConsumer<SyncObject, MethodThread> {}
