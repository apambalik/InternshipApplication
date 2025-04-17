package ADT;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayList<T> implements ListInterface<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(T item) {
        ensureCapacity();
        array[size++] = item;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public void set(int index, T item) {
        checkIndex(index);
        array[index] = item;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removed = array[index];
        
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index+1, array, index, numMoved);
        }
        
        array[--size] = null; // Clear to let GC do its work
        return removed;
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

    @Override
    public boolean contains(T element) {
        return indexOf(element) >= 0;
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
    public void swap(int i, int j) {
        checkIndex(i);
        checkIndex(j);
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private void ensureCapacity() {
        if (size == array.length) {
            int newCapacity = array.length * 2;
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return -1;
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