
# Caching Library

## Overview

This caching library provides a flexible and customizable in-memory caching solution for Java applications. It offers support for multiple eviction policies, including FIFO, LIFO, LRU, and LFU, allowing developers to choose the most suitable caching strategy for their specific use cases.

## Features

- **Support for Multiple Eviction Policies**: Choose from FIFO, LIFO, LRU, and LFU eviction policies to manage cache entries based on different criteria.
  
- **Custom Eviction Policies**: Extend the `EvictionPolicy` interface to implement custom eviction policies tailored to your application's requirements.

- **Thread Safety**: Ensure thread-safe operations with built-in support for concurrency using `ReentrantReadWriteLock`.

## Getting Started

### Installation

```bash
./gradlew build
./gradlew run
```

### Usage

1. Choose Eviction Policy: Instantiate an eviction policy of your choice (e.g., LRUEvictionPolicy, FIFOEvictionPolicy) with the desired initial capacity.

2. Create Cache Instance: Initialize a cache instance by passing the chosen eviction policy.

3. Cache Operations: Use the cache instance to perform cache operations such as put, get, and remove.

### Custom Eviction Policy

To implement a custom eviction policy, create a class that implements the EvictionPolicy interface and override the necessary methods (onPut, onGet, evict).

```java
// cachelibrary/evictionpolicy/CustomEvictionPolicy.java
public class CustomEvictionPolicy<K> implements EvictionPolicy<K> {
    // Implement custom eviction policy logic here
}
```

### Documentation

- Cache: Interface defining cache operations (put, get, remove).
- CacheImpl: Implementation of the cache interface with support for customizable eviction policies.
- EvictionPolicy: Interface defining methods for eviction policies (onPut, onGet, evict).
- LRUEvictionPolicy: Implementation of the LRU eviction policy.
- FIFOEvictionPolicy: Implementation of the FIFO eviction policy.
- LIFOEvictionPolicy: Implementation of the LIFO eviction policy.
- LFUEvictionPolicy: Implementation of the LFU eviction policy.

### Methods Explained

1. LRUEvictionPolicy:
   - onPut: Updates the access order of the key.
   - onGet: Updates the access order of the key.
   - evict: Evicts the least recently used entry when the cache is full.
  
2. FIFOEvictionPolicy:
    - onPut: Adds the key to the end of the queue.
    - onGet: No action required.
    - evict: Evicts the first entry when the cache is full.

3. LIFOEvictionPolicy:
    - onPut: Adds the key to the front of the queue.
    - onGet: No action required.
    - evict: Evicts the last entry when the cache is full.

4. LFUEvictionPolicy:
    - onPut: Adds the key to the frequency map with a count of 1.
    - onGet: Updates the frequency of the key.
    - evict: Evicts the least frequently used entry when the cache is full.

### Example run explained

put 1,2,3,4,5,6
get 1,2
evict

1. LRU: 3,4,5,6,1,2 will be the ordered part and `3` will be evicted
2. FIFO: no impact of get and first one will get out which means `1` will be evicted
3. LIFO: no impact of get and last one will get out which means `6` will be evicted
4. LFU: 3,4,5,6,2,1 will be the ordered part and `3` will be evicted
