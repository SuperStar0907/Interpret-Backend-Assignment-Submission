// LRUEvictionPolicy.java
package cachelibrary.evictionpolicy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private Map<K, Long> accessOrder;

    public LRUEvictionPolicy() {
        // true for access-ordering
        accessOrder = new LinkedHashMap<>(0, 0.75f, true);
    }

    /** 
     * @param key
     */
    @Override
    public void onPut(K key) {
        accessOrder.put(key, System.nanoTime());
    }

    @Override
    public void onGet(K key) {
        // The act of getting also counts as accessing, so update access time
        accessOrder.put(key, System.nanoTime());
    }

    @Override
    public K evict() {
        if (accessOrder.isEmpty()) {
            return null; // Nothing to evict if cache is empty
        }
        // Get the key with the minimum access time (i.e., the least recently used)
        K keyToEvict = Collections.min(accessOrder.entrySet(), Map.Entry.comparingByValue()).getKey();
        accessOrder.remove(keyToEvict);
        return keyToEvict;
    }

    // Method to access the contents of the cache
    public Map<K, Long> getCacheContents() {
        return accessOrder;
    }
}