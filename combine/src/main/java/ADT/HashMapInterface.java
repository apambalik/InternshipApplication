package ADT;

import java.util.List;

public interface HashMapInterface<K, V> {
    void put(K key, V value);
    V get(K key);
    V getOrDefault(K key, V defaultValue);
    V remove(K key);
    boolean containsKey(K key);
    int size();
    boolean isEmpty();
    void clear();
    
    // Additional utilities
    K getKeyAtIndex(int index);
    List<V> values();
    int capacity();
}