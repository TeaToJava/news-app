package ru.clevertec.cachestarter.cache.lru;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class LRUBaseCache<K, V> extends AbstractValueAdaptingCache {
    private final int MAP_CAPACITY = 16;
    private final float MAP_LOAD_FACTOR = 0.75f;
    private final boolean MAP_ACCESS_ORDER = true;
    private final String name;
    private Map<K, V> cache;

    public LRUBaseCache(boolean allowNullValues, String name) {
        super(allowNullValues);
        this.name = name;
    }

    public LRUBaseCache(boolean allowNullValues, String name, int size) {
        super(allowNullValues);
        this.name = name;
        cache = Collections.synchronizedMap(new CustomLinkedHashMap<K, V>
                (MAP_CAPACITY, MAP_LOAD_FACTOR, MAP_ACCESS_ORDER, size));
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void put(Object key, Object value) {
        cache.put((K) key, (V) value);
    }

    @Override
    public void evict(Object key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    @Override
    protected Object lookup(Object key) {
        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        cache.computeIfAbsent((K) key, k -> {
            try {
                T t = valueLoader.call();
                Object object = toStoreValue(t);
                return (V) object;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return (T) cache.get(key);
    }


    static class CustomLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        private static int MAX_ENTRIES;

        public CustomLinkedHashMap() {

        }

        public CustomLinkedHashMap(
                int initialCapacity, float loadFactor, boolean accessOrder, int entries) {
            super(initialCapacity, loadFactor, accessOrder);
            MAX_ENTRIES = entries;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    }
}
