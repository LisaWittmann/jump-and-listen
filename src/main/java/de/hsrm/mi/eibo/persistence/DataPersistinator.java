package de.hsrm.mi.eibo.persistence;

import java.util.List;

public interface DataPersistinator<T> {

    void saveData(List<T> data);
    void saveData(T data);
    List<T> loadData();
    
}