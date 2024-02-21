// CacheImpl.java
package cachelibrary.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import cachelibrary.evictionpolicy.EvictionPolicy;

public class CacheImpl<K, V> implements Cache<K, V> {
    private Map<K, V> cacheMap;
    private EvictionPolicy<K> evictionPolicy;

    // Lock to protect the cache and implement thread-safe operations
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CacheImpl(EvictionPolicy<K> evictionPolicy) {
        cacheMap = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            if (cacheMap.containsKey(key)) {
                cacheMap.put(key, value);
                evictionPolicy.onGet(key);
            } else {
                if (cacheMap.size() >= 100) { // Example size limit
                    K evictedKey = evictionPolicy.evict();
                    cacheMap.remove(evictedKey);
                }
                cacheMap.put(key, value);
                evictionPolicy.onPut(key);
            }
        } catch (Exception e) {
            // Handle exceptions gracefully
            System.err.println("An error occurred while putting key-value pair in the cache: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public V get(K key) {
        lock.readLock().lock();
        try {
            V value = cacheMap.get(key);
            if (value != null) {
                evictionPolicy.onGet(key);
            }
            return value;
        } catch (Exception e) {
            // Handle exceptions gracefully
            System.err.println("An error occurred while getting value from the cache: " + e.getMessage());
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void remove(K key) {
        lock.writeLock().lock();
        try {
            cacheMap.remove(key);
        } catch (Exception e) {
            // Handle exceptions gracefully
            System.err.println("An error occurred while removing key from the cache: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Methods to access the eviction policy and cache contents
    public EvictionPolicy<K> getEvictionPolicy() {
        return evictionPolicy;
    }

    public Map<K, V> getCacheContents() {
        return cacheMap;
    }
}