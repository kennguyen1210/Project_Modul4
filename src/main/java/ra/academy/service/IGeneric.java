package ra.academy.service;

import java.util.List;

public interface IGeneric <T,E>{
    List<T> findAll();
    T findById(E id);
    void save(T t);
    void delete(E id);
    void update(T t);
}
