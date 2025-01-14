package ru.clevertec.cachestarter.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ru.clevertec.cachestarter.cache.lfu.LFUBaseCache;
import ru.clevertec.cachestarter.cache.lru.LRUBaseCache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManagerImpl implements CacheManager {
    private final CacheType cacheType;
    /**
     * Map кешей с их именами
     */
    private Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
    /**
     * Максимальный размер коллекции кеша, получаемый из properties
     */
    private final int cacheSize;

    public CacheManagerImpl(CacheType cacheType, int size) {
        this.cacheType = cacheType;
        this.cacheSize = size;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            switch (cacheType) {
                case LRU:
                    cache = new LRUBaseCache(false, name, cacheSize);
                    break;
                default:
                    cache = new LFUBaseCache(false, name, cacheSize);
            }
            cacheMap.put(name, cache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}
