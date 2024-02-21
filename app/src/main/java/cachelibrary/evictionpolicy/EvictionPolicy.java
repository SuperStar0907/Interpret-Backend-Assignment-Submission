package cachelibrary.evictionpolicy;

/**
 * Interface representing an eviction policy for a cache.
 * <p>
 * Eviction policies determine the strategy used to remove entries from the cache
 * when the cache reaches its maximum capacity.
 *
 * @param <K> The type of keys in the cache.
 */
public interface EvictionPolicy<K> {

    /**
     * This method is called when a new key-value pair is added to the cache.
     * Implementations of this method can update internal data structures to
     * reflect the addition of the new key.
     *
     * @param key The key that was added to the cache.
     */
    void onPut(K key);

    /**
     * This method is called when an existing key is accessed (i.e., a 'get' operation)
     * in the cache. Implementations of this method can update internal data structures
     * to reflect the access pattern of the key.
     *
     * @param key The key that was accessed in the cache.
     */
    void onGet(K key);

    /**
     * This method is called to evict an entry from the cache based on the eviction policy.
     * Implementations of this method should determine which entry to evict from the cache
     * based on the specific eviction strategy employed by the policy.
     *
     * @return The key of the entry to evict from the cache, or null if the cache is empty.
     */
    K evict();
}