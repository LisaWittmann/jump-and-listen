package de.hsrm.mi.eibo.persistence;

import java.util.List;

/**
 * Interface zum einheitlichen Speichern und Laden von Daten
 * 
 * @param <T> zu speichernder Datentyp
 * @author pwieg001, lwitt0001, lgers001
 */
public interface DataPersistinator<T> {

    void saveData(T data);

    void saveAll(List<T> data);

    T loadData();

    List<T> loadAll();

}