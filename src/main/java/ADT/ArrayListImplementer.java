package ADT;

/**
 *
 * @author leong kah tian
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

//dynamic resize array
public class ArrayListImplementer<T> implements ListInterface<T> {
    private static final int DEFAULT_CAPACITY = 10; //initial size of the array
    private T[] data;
    private int size;

    public ArrayListImplementer() {
        data = (T[]) new Object[DEFAULT_CAPACITY]; // Generic array creation
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2); // Expands array size in double
        }
    }

    @Override
    public void add(T item) {
        ensureCapacity();
        data[size++] = item; // Add data and increase size
    }

    @Override
    //get the data with specific input
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    @Override
    //update the data with new input
    public void set(int index, T item) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = item;
    }

    @Override
    //delete dtaa with specific input
    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T removed = data[index];
        
        //move the data to cover empty place
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;// Remove last duplicate element and decrease size
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    // swap data dor QuickSort
    public void swap(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) throw new IndexOutOfBoundsException();
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[index++];
            }
        };
    }
    
}
