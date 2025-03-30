/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT;

/**
 *
 * @author Goh Ee Lin
 */
public class HashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private Entry<K, V>[] table;
    private int size;
    private int threshold;
    
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Entry[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
        size = 0;
    }
    
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        
        addEntry(hash, key, value, index);
        return null;
    }
    
    @SuppressWarnings("unchecked")
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
    
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
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
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    @SuppressWarnings("unchecked")
    public K getKeyAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        int count = 0;
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                if (count == index) {
                    return e.key;
                }
                count++;
            }
        }
        
        return null;  // Should never reach here
    }
    
    private int hash(K key) {
        return key.hashCode();
    }
    
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }
    
    private void addEntry(int hash, K key, V value, int index) {
        if (size >= threshold) {
            resize(2 * table.length);
            hash = hash(key);
            index = indexFor(hash, table.length);
        }
        
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        size++;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        Entry<K, V>[] newTable = new Entry[newCapacity];
        
        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> e = oldTable[i];
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(hash(e.key), newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
        
        table = newTable;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }
}
