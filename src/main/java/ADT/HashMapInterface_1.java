/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

/**
 *
 * @author Goh Ee Lin
 */
public interface HashMapInterface_1<K, V> {

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();
}
