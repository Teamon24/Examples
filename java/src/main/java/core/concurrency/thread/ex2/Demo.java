package core.concurrency.thread.ex2;

import org.apache.commons.lang3.tuple.Pair;
import core.concurrency.thread.ThreadUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {
        SyncObject syncObject = new SyncObject();

        BiConsumer<SyncObject, SyncThread> sync1 = SyncObject::sync1;
        BiConsumer<SyncObject, SyncThread> sync2 = SyncObject::sync2;
        BiConsumer<SyncObject, SyncThread> notSync = SyncObject::notSync;

        Stream<BiConsumer<SyncObject, SyncThread>> methods =
            Stream.of(sync1, sync2, notSync);

        int threadsAmount = 10;

        Map<BiConsumer<SyncObject, SyncThread>, List<SyncThread>> methodsAndThreads =
            getMethodsAndThreads((id) -> new SyncThread(id, syncObject), methods, threadsAmount);

        setMethods(methodsAndThreads);

        Stream.of(sync1, sync2)
            .map(methodsAndThreads::get)
            .flatMap(Collection::stream)
            .forEach(Thread::start);

        methodsAndThreads
            .get(notSync)
            .forEach(Thread::start);
    }

    private static Map<BiConsumer<SyncObject, SyncThread>, List<SyncThread>>
    getMethodsAndThreads(final Function<Integer, SyncThread> create,
                         final Stream<BiConsumer<SyncObject, SyncThread>> methods, int threadsAmount)
    {

        return methods
            .map(it -> Pair.of(it, ThreadUtils.createThreads(threadsAmount, create)))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static void setMethods(final Map<BiConsumer<SyncObject, SyncThread>, List<SyncThread>> collect) {
        collect.forEach((syncObjectSyncThreadBiConsumer, syncThreads) ->
                syncThreads.forEach(syncThread ->
                    syncThread.setMethod(syncObjectSyncThreadBiConsumer)));
    }
}
