package ru.clevertec.cachestarter.cache.lfu;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.Callable;

public class LFUBaseCache<K, V> extends AbstractValueAdaptingCache {
    private static final int INITIAL_FREQUENCY = 1;
    private final String name;
    private int size;
    private int minFrequency = 0;
    private Map<K, Node<K, V>> cache = new HashMap<>();

    private Map<Integer, LinkedHashSet<Node<K, V>>> frequencyMap =
            new HashMap<>();

    public LFUBaseCache(boolean allowNullValues, String name) {
        super(allowNullValues);
        this.name = name;
    }

    public LFUBaseCache(boolean allowNullValues, String name, int size) {
        super(allowNullValues);
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public synchronized void put(Object key, Object value) {
        Node node = cache.get(key);
        if (node == null) {
            if (cache.size() == size) {
                deleteNodeWithMinFrequency();
            }
            addNewNode(key, value);
        } else {
            updateNode(node);
        }
    }

    @Override
    public synchronized void evict(Object key) {
        Node node = cache.get(key);
        int frequency = node.getFrequency();
        LinkedHashSet<Node<K, V>> currentFrequencyList = frequencyMap.get(frequency);
        currentFrequencyList.remove(node);
        frequencyMap.put(frequency, currentFrequencyList);
    }

    @Override
    public void clear() {
        frequencyMap.clear();
        cache.clear();
    }

    @Override
    public Object getNativeCache() {
        return cache;
    }

    @Override
    protected synchronized Object lookup(Object key) {
        if (!cache.containsKey(key)) {
            return null;
        } else {
            return updateAndReturnNode(key).getValue();
        }
    }

    @Override
    public synchronized <T> T get(Object key, Callable<T> valueLoader) {
        cache.computeIfAbsent((K) key, k -> {
            try {
                T t = valueLoader.call();
                Object object = toStoreValue(t);
                return (Node<K, V>) new Node<>(key, object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return (T) cache.get(key);
    }

    private synchronized Node updateAndReturnNode(Object key) {
        Node node = cache.get(key);
        if (node != null) {
            updateNode(node);
        }
        return node;
    }

    private void deleteNodeWithMinFrequency() {
        LinkedHashSet<Node<K, V>> frequencyList = frequencyMap.get(minFrequency);
        if (frequencyList != null) {
            frequencyList.removeFirst();
        }
    }

    private void addNewNode(Object key, Object value) {
        Node node = new Node(key, value);
        cache.put((K) key, node);
        LinkedHashSet<Node<K, V>> frequencyList = frequencyMap.get(INITIAL_FREQUENCY);
        if (frequencyList == null) {
            frequencyList = new LinkedHashSet<>();
        }
        frequencyList.add(node);
        frequencyMap.put(INITIAL_FREQUENCY, frequencyList);
        minFrequency = minFrequency > INITIAL_FREQUENCY ? INITIAL_FREQUENCY : minFrequency;
    }

    private synchronized void updateNode(Node node) {
        int frequency = node.getFrequency();
        LinkedHashSet<Node<K, V>> currentFrequencyList = frequencyMap.get(frequency);
        currentFrequencyList.remove(node);
        node.setFrequency(frequency++);
        cache.put((K) node.getKey(), node);
        LinkedHashSet<Node<K, V>> newFrequencyList = frequencyMap.get(frequency);
        if (newFrequencyList == null) {
            newFrequencyList = new LinkedHashSet<>();
        }
        newFrequencyList.add(node);
        frequencyMap.put(frequency, newFrequencyList);
        if (minFrequency > frequency) {
            minFrequency = frequency;
        }
    }
}
