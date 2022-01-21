package core.concurrency.thread.ex2;

import java.util.function.BiConsumer;

interface SyncMethod extends BiConsumer<SyncObject, MethodThread> {}
