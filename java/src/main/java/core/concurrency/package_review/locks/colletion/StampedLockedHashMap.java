package core.concurrency.package_review.locks.colletion;

import utils.ConcurrencyUtils;

import java.util.HashMap;
import java.util.concurrent.locks.StampedLock;

public class StampedLockedHashMap<K, V> {

    private final HashMap<K, V> hashMap = new HashMap<>();
    private final StampedLock stampedLock = new StampedLock();

    public void put(K key, V value) {
        long stamp = stampedLock.writeLock();
        try {
            hashMap.put(key, value);
        } finally {
            ConcurrencyUtils.threadPrintln("put was executed");
            stampedLock.unlockWrite(stamp);
        }
    }

    public V get(K key) {
        long stamp = stampedLock.readLock();
        try {
            return hashMap.get(key);
        } finally {
            ConcurrencyUtils.threadPrintln("get: read was executed");
            stampedLock.unlockRead(stamp);
        }
    }

    public V optimisticGet(K key) {
        long stamp = stampedLock.tryOptimisticRead();
        V value = hashMap.get(key);
        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                return hashMap.get(key);
            } finally {
                ConcurrencyUtils.threadPrintln("optimisticGet: read was executed");
                stampedLock.unlock(stamp);
            }
        }
        ConcurrencyUtils.threadPrintln("optimisticGet: optimistic read was executed");
        return value;
    }
}
