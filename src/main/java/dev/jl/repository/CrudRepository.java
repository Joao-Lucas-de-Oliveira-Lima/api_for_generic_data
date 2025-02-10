package dev.jl.repository;

import java.util.List;

public interface CrudRepository<T> {

    T findById(Long id);
    T save(T object);
    void delete(Long id);
    T updateById(T update);
    List<T> findAll();
}