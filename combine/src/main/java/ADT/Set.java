package ADT;

public interface Set<T> extends Collection<T> {
    // Set-specific methods can be added here
    boolean containsAll(Collection<T> elements);
}