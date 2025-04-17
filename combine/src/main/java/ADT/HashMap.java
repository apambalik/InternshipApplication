package ADT;

import java.util.ArrayList;
import java.util.List;

public class HashMap<K, V> implements HashMapInterface<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    
    private Entry<K, V>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Entry[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        // Check for existing key
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                e.value = value;
                return;
            }
        }
        
        // Add new entry
        addEntry(hash, key, value, index);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        Entry<K, V> prev = null;
        Entry<K, V> e = table[index];
        
        while (e != null) {
            if (key.equals(e.key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public K getKeyAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int count = 0;
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> e = entry; e != null; e = e.next) {
                if (count == index) {
                    return e.key;
                }
                count++;
            }
        }
        return null;
    }

    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> e = entry; e != null; e = e.next) {
                values.add(e.value);
            }
        }
        return values;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    private int hash(K key) {
        return key.hashCode();
    }
    
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }
    
    private void addEntry(int hash, K key, V value, int bucketIndex) {
        if (size >= threshold) {
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }
        
        createEntry(hash, key, value, bucketIndex);
    }
    
    private void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(key, value, e);
        size++;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        Entry<K, V>[] newTable = new Entry[newCapacity];
        
        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = indexFor(hash(entry.key), newCapacity);
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }
        
        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }
}