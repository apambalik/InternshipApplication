/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ADT;

/**
 *
 * @author Goh Ee Lin
 */
public interface ListInterface1<T> {
    public boolean add(T elements);
    
    public T get(int index);
    
    public void set(int index, T element);
    
    public boolean remove(int index);
    
    public boolean remove(T element);
    
    public int size();
    
    public boolean isEmpty();

}
