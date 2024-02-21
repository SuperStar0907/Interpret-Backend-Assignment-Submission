// LFUEvictionPolicy.java
package cachelibrary.evictionpolicy;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    private int minFreq;
    private final Map<K, Integer> keyToValueMap;
    private final Map<K, Integer> keyToFrequencyMap;
    private final Map<Integer, LinkedHashSet<K>> frequencyToLRUMap;

    public LFUEvictionPolicy() {
        minFreq = 0;
        keyToValueMap = new HashMap<>();
        keyToFrequencyMap = new HashMap<>();
        frequencyToLRUMap = new HashMap<>();
    }

    @Override
    public void onPut(K key) {
        if (keyToValueMap.containsKey(key)) {
            onGet(key); // Update key's frequency
            return;
        }

        minFreq = 1;
        putFreq(key, minFreq); // Add new key and frequency
        keyToValueMap.put(key, null); // Add new key
    }

    @Override
    public void onGet(K key) {
        if (!keyToValueMap.containsKey(key))
            return;

        final int freq = keyToFrequencyMap.get(key);
        frequencyToLRUMap.get(freq).remove(key);
        if (freq == minFreq && frequencyToLRUMap.get(freq).isEmpty()) {
            frequencyToLRUMap.remove(freq);
            ++minFreq;
        }

        putFreq(key, freq + 1);
    }

    @Override
    public K evict() {
        if (frequencyToLRUMap.isEmpty()) {
            return null; // No elements to evict
        }

        LinkedHashSet<K> minFreqKeys = frequencyToLRUMap.get(minFreq);
        K keyToEvict = minFreqKeys.iterator().next();
        minFreqKeys.remove(keyToEvict);

        if (minFreqKeys.isEmpty()) {
            frequencyToLRUMap.remove(minFreq);
        }

        keyToValueMap.remove(keyToEvict);
        keyToFrequencyMap.remove(keyToEvict);

        return keyToEvict;
    }

    private void putFreq(K key, int freq) {
        keyToFrequencyMap.put(key, freq);
        frequencyToLRUMap.putIfAbsent(freq, new LinkedHashSet<>());
        frequencyToLRUMap.get(freq).add(key);
    }
}