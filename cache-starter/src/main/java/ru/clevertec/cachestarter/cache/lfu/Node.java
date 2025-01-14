package ru.clevertec.cachestarter.cache.lfu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Node<K, V> {
    private final int INITIAL_FREQUENCY = 1;
    private K key;
    private V value;
    private int frequency;
    private Node next;
    private Node previous;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.frequency = INITIAL_FREQUENCY ;
    }
}


