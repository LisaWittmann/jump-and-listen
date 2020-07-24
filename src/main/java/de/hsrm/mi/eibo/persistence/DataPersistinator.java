package de.hsrm.mi.eibo.persistence;

import java.io.IOException;
import java.util.List;

public interface DataPersistinator<T> {

    void saveData(List<T> data) throws IOException;

    List<T> loadData() throws IOException;
    
}