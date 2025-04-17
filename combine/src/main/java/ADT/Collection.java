package ADT;

public interface Collection<T> {
    boolean add(T element);
    boolean remove(T element);
    boolean contains(T element);
    int size();
    boolean isEmpty();
    Iterator<T> iterator();
}