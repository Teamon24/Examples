package core.concurrency.thread.ex4;

import utils.ConcurrencyUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {
        SyncObject syncObject = new SyncObject();

        SyncMethod sync1 = SyncObject::sync1;
        SyncMethod sync2 = SyncObject::sync2;
        SyncMethod notSync = SyncObject::notSync;

        int threadsAmount = 10;
        int jobImitationTime = 5000;

        Function<Integer, MethodThread> constructorForSync =
            (id) -> new MethodThread(id, syncObject, jobImitationTime);

        Function<Integer, MethodThread> constructorForNotSync =
            (id) -> new MethodThread(id, syncObject, threadsAmount * jobImitationTime);

        List<MethodThread> notSyncThreads = createThreads(threadsAmount, constructorForNotSync, notSync);
        List<MethodThread> syncThreads = createThreads(threadsAmount, constructorForSync, sync1, sync2);

        syncThreads.forEach(Thread::start);
        notSyncThreads.forEach(Thread::start);
        ConcurrencyUtils.threadPrintln("IM OUT");
    }

    private static List<MethodThread> createThreads(
        final int threadsAmount,
        final Function<Integer, MethodThread> constructor,
        final SyncMethod... syncMethods
    ) {
        List<MethodThread> threads = Stream
            .of(syncMethods)
            .map(it -> createThreads(threadsAmount, constructor, it))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        Collections.shuffle(threads);

        return threads;
    }

    private static List<MethodThread> createThreads(
        final int threadsAmount,
        final Function<Integer, MethodThread> constructor,
        final SyncMethod syncMethod
    ) {
        List<MethodThread> threads = ConcurrencyUtils.createThreads(threadsAmount, constructor);
        threads.forEach(it -> it.setMethod(syncMethod));
        return threads;
    }
}