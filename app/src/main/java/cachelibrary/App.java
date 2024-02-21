// App.java
package cachelibrary;

import java.util.Map;

import cachelibrary.cache.Cache;
import cachelibrary.cache.CacheImpl;
import cachelibrary.evictionpolicy.EvictionPolicy;
import cachelibrary.evictionpolicy.FIFOEvictionPolicy;
import cachelibrary.evictionpolicy.LFUEvictionPolicy;
import cachelibrary.evictionpolicy.LIFOEvictionPolicy;
import cachelibrary.evictionpolicy.LRUEvictionPolicy;

public class App {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Order of adding as example: " + "key1, key2, key3, key4, key5, key6");
        System.out.println("Order of access for Testing: " + "key1, key1, key2");

        testEvictionPolicy(new LRUEvictionPolicy<>(), "LRU");
        testEvictionPolicy(new FIFOEvictionPolicy<>(), "FIFO");
        testEvictionPolicy(new LIFOEvictionPolicy<>(), "LIFO");
        testEvictionPolicy(new LFUEvictionPolicy<>(), "LFU");
    }

    private static void testEvictionPolicy(EvictionPolicy<String> evictionPolicy, String policyName) {
        Cache<String, String> cache = new CacheImpl<>(evictionPolicy);

        // Use the cache
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");
        cache.put("key6", "value6");

        // Access some keys
        cache.get("key1");
        cache.get("key1");
        cache.get("key2");

        // Evict an item
        String evictedKey = evictionPolicy.evict();
        // remove the evicted key from the cache
        cache.remove(evictedKey);
        if (evictedKey != null) {
            System.out.println("\n" + policyName + " Cache evicted key: " + evictedKey);
        } else {
            System.out.println("\n" + policyName + " Cache is empty, nothing to evict.");
        }
    }
}