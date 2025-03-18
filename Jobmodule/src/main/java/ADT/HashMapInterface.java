/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

/**
 *
 * @author leong kah tian
 */

import java.util.List;

public interface HashMapInterface<K, V> {

    // add a new data 
    public void put(K key, V value);

    // retrieve the unique key
    public K getKey(int index);

    // retrieve using unique key
    public V getValue(K key);

  
    public V get(K key); 

    // retrieve all values 
    public List<V> values(); 

    // remove from the hashmap
    public V remove(K key);

    // check whether the unique key input exists or not
    public boolean containsKey(K key);

    public int size();

    public int capacity();

    // check whether the hashmap is empty
    public boolean isEmpty();

    // check whether the hashmap is full
    public boolean isFull();

    // clear all entries in the hashmap
    public void clear();
}
