// LIFOEvictionPolicy.java
package cachelibrary.evictionpolicy;

import java.util.ArrayDeque;
import java.util.Deque;

public class LIFOEvictionPolicy<K> implements EvictionPolicy<K> {
    private Deque<K> accessOrder;

    public LIFOEvictionPolicy() {
        accessOrder = new ArrayDeque<>();
    }

    /**
     * @param key
     */
    @Override
    public void onPut(K key) {
        try {
            accessOrder.addFirst(key); // Add the new item to the top of the deque
        } catch (Exception e) {
            System.err.println("An error occurred while putting key in the LIFO eviction policy: " + e.getMessage());
        }
    }

    @Override
    public void onGet(K key) {
        // For LIFO, getting an item doesn't affect the eviction order
        if(key == null) {
            System.err.println("Key cannot be null");
            return;
        }
    }

    @Override
    public K evict() {
        if (!accessOrder.isEmpty()) {
            return accessOrder.pop(); // Evict the newest item
        }
        return null; // Return null if the cache is empty
    }
}
