/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

/**
 *
 * @author leong kah tian
 */


import java.util.ArrayList;
import java.util.List;

public class HashMapImplementer<K, V> implements HashMapInterface<K, V> {

    private static final int DEFAULT_CAPACITY = 16; // Default initial capacity
    private static final double LOAD_FACTOR = 0.75; // Load factor for resizing

    // Array of linked list entries
    private Entry<K, V>[] buckets;
    private int size;

    public HashMapImplementer() {
        this(DEFAULT_CAPACITY);
    }

    public HashMapImplementer(int capacity) {
        buckets = new Entry[capacity];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    @Override
    //Inserts a key-value pair into the hash map.
    public void put(K key, V value) {
        int index = hash(key);

        if (buckets[index] == null) {
            buckets[index] = new Entry<>(key, value);
            size++;
        } else {
            Entry<K, V> current = buckets[index];
            while (current != null) {
                if (current.getKey().equals(key)) {
                    current.setValue(value); // Update existing key
                    return;
                }
                current = current.getNext();
            }

            // Add new entry at the end of the chain
            Entry<K, V> newEntry = new Entry<>(key, value);
            newEntry.setNext(buckets[index]);
            buckets[index] = newEntry;
            size++;
        }

         // Resize if load factor exceeded
        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    @Override
    //Retrieves the key at a specific input in hash table.
    public K getKey(int index) {
        if (index < 0 || index >= buckets.length) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }

        for (Entry<K, V> entry : buckets) {
            if (entry != null && hash(entry.getKey()) == index) {
                return entry.getKey();
            }
        }

        return null;
    }

    @Override
    public V getValue(K key) {
        return get(key); // Use get() method to retrieve the value
    }

    @Override
    public V get(K key) {
        int index = hash(key);

        Entry<K, V> current = buckets[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }

        return null;
    }

    @Override
    //get all data in hash map
    public List<V> values() {
        List<V> valuesList = new ArrayList<>();
        for (Entry<K, V> entry : buckets) {
            while (entry != null) {
                valuesList.add(entry.getValue());
                entry = entry.getNext();
            }
        }
        return valuesList;
    }

    @Override
    //delete key-value pair in hash map
    public V remove(K key) {
        int index = hash(key);

        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev != null) {
                    prev.setNext(current.getNext()); // Remove entry from chain
                } else {
                    buckets[index] = current.getNext(); // Update head of chain
                }
                size--;
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }

        return null;
    }

    @Override
    //check the key exist or not
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
    public boolean isFull() {
        return (double) size / buckets.length >= LOAD_FACTOR;
    }

    @Override
    //Clear the hash map
    public void clear() {
        buckets = new Entry[buckets.length];
        size = 0;
    }

    @Override
    public int capacity() {
        return buckets.length;
    }

    //resize hash map in double 
    private void resize() {
    Entry<K, V>[] oldBuckets = buckets;
    buckets = new Entry[oldBuckets.length * 2]; 
    size = 0; // Reset size and reload in put()

    for (Entry<K, V> entry : oldBuckets) {
        while (entry != null) {
            put(entry.getKey(), entry.getValue()); //reinsert using put()
            entry = entry.getNext();
        }
    }
}


    private static class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next; // Pointer for separate chaining

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }
    }
}
