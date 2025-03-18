/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author Goh Ee Lin
 */
public interface Collection<T> {

    boolean add(T element);

    boolean remove(T element);

    boolean contains(T element);

    int size();

    boolean isEmpty();

    Iterator<T> iterator();

}
