package org.example.api.service;

import java.util.List;

public interface GameRestService<T> {

    List<T> find();

    T findById(int id);

    int create(T entity);

    boolean update(int id, T entity);

    boolean deleteById(int id);

}
