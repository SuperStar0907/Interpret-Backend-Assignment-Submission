// FIFOEvictionPolicy.java
package cachelibrary.evictionpolicy;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOEvictionPolicy<K> implements EvictionPolicy<K> {
    private Queue<K> accessOrder;

    public FIFOEvictionPolicy() {
        accessOrder = new LinkedList<>();
    }

    /** 
     * @param key
     */
    @Override
    public void onPut(K key) {
        if (key == null) {
            System.err.println("Key cannot be null");
        }
        accessOrder.offer(key); // Add the new item to the end of the queue
    }

    @Override
    public void onGet(K key) {
        // For FIFO, getting an item doesn't affect the eviction order
        if (key == null) {
            System.err.println("Key cannot be null");
        }
    }

    @Override
    public K evict() {
        if (accessOrder.isEmpty()) {
            return null; // Return null if the cache is empty
        }
        // return and remove the oldest item
        return accessOrder.poll();
    }
}