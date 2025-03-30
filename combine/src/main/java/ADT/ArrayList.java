package ADT;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 *
 * @author Goh Ee Lin
 */
public class ArrayList<T> implements ListInterface1<T>, Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        size = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T element) {
        ensureCapacity();
        array[size++] = element;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public void set(int index, T element) {
        checkIndex(index);
        array[index] = element;
    }

    @Override
    public boolean remove(int index) {
        checkIndex(index);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Prevent memory leak
        return true;
    }

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                array[--size] = null;
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

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == array.length) {
            T[] newElements = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newElements, 0, size);
            array = newElements;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[currentIndex++];
        }
    }
}