package com.kata;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thanks to @iasemenov on GitHub
 */
public final class ConcurrentModelLocker<T> {
    private final ConcurrentMap<T, ReentrantLock> locksMap;

    /**
     * Constructs new locker using @see java.util.concurrent.ConcurrentHashMap for
     * id-lock mapping
     */
    public ConcurrentModelLocker() {
        this.locksMap = new ConcurrentHashMap<>();
    }

    public final void lockEntity(final T entityId) {
        final ReentrantLock lock = getMappedLock(entityId);
        lock.lock();
    }

    private final ReentrantLock getMappedLock(final T entityId) {
        return locksMap.computeIfAbsent(entityId, id -> new ReentrantLock());
    }

    public final void unlockEntity(final T entityId) {
        final ReentrantLock lock = locksMap.get(entityId);
        // Remove the lock from map if no threads are waiting on it to conserve memory
        if (lock != null) {
            lock.unlock();
        }
    }
}