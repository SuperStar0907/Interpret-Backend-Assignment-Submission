// Cache.java
package cachelibrary.cache;

/**
 * Interface for cache implementations.
 */
public interface Cache<K, V> {
    /**
     * Adds a key-value pair to the cache.
     * @param key The key.
     * @param value The value.
     */
    void put(K key, V value);

    /**
     * Retrieves the value associated with the given key from the cache.
     * @param key The key.
     * @return The value associated with the key, or null if the key is not present in the cache.
     */
    V get(K key);

    /**
     * Removes the entry associated with the given key from the cache.
     * @param key The key.
     */
    void remove(K key);
}