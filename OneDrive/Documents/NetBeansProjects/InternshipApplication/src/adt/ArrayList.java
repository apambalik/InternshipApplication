/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.io.Serializable;
import adt.ListInterface;

/**
 *
 * @author Goh Ee Lin
 */
public class ArrayList<T> implements ListInterface<T>, Serializable {

    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        size = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T element) {
        ensureCapacity();
        array[size] = element;
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array[index] = element;
    }

    @Override
    public boolean remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        array[--size] = null; // Clear reference for GC
        return true;
    }

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            T[] newElements = (T[]) new Object[array.length * 2];
            for (int i = 0; i < size; i++) {
                newElements[i] = array[i];
            }
            array = newElements;
        }
    }


    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new RuntimeException("No more elements");
                }
                return array[index++];
            }
        };
    }
}
