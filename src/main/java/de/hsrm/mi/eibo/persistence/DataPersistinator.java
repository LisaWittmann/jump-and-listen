package de.hsrm.mi.eibo.persistence;

import java.util.List;

public interface DataPersistinator<T> {

    void saveData(T data);
    void saveAll(List<T> data);
    T loadData();
    List<T> loadAll();
    
}