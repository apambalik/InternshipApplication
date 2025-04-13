package ADT;

/**
 *
 * @author leong kah tian
 */

public interface ListInterface<T> {
    //add data
    void add(T item);
    
    //get data
    T get(int index);
    
    //update and replace the data
    void set(int index, T item);
    
    //delete data
    T remove(int index);
    
    //returns the number of data list
    int size();
    
    //swaping for QuickSort
    void swap(int i, int j);

    
}
