package de.hsrm.mi.eibo.persistence;

public interface DataPersistinator<T> {

    void saveData(T data);
    T loadData();
    
}