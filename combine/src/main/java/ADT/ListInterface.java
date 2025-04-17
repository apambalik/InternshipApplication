package ADT;

public interface ListInterface<T> {
    // Basic operations
    boolean add(T item);
    T get(int index);
    void set(int index, T item);
    T remove(int index);
    boolean remove(T element);
    int size();
    boolean isEmpty();
    
    // Utility operations
    void swap(int i, int j);
    boolean contains(T element);
    Iterator<T> iterator();
}