package core.concurrency.package_review.locks.colletion;

import utils.ConcurrencyUtils;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockedHashMap<K, V> {
    private final HashMap<K, V> hashMap = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public int size() {
        return hashMap.size();
    }

    public V put(K key, V value) {
        this.writeLock.lock();
        try {
            return this.hashMap.put(key, value);
        } finally {
            ConcurrencyUtils.threadPrintln("PUT was executed");
            this.writeLock.unlock();
        }
    }

    public V remove(Object key) {
        this.writeLock.lock();
        try {
            return this.hashMap.remove(key);
        } finally {
            ConcurrencyUtils.threadPrintln("REMOVE was executed");
            this.writeLock.unlock();
        }
    }

    public V get(Object key) {
        this.readLock.lock();
        try {
            return this.hashMap.get(key);
        } finally {
            ConcurrencyUtils.threadPrintln("GET was executed");
            this.readLock.unlock();
        }
    }

    public boolean containsKey(Object key) {
        this.readLock.lock();
        try {
            return this.hashMap.containsKey(key);
        } finally {
            ConcurrencyUtils.threadPrintln("CONTAINSKEY was executed");
            this.readLock.unlock();
        }
    }


}
