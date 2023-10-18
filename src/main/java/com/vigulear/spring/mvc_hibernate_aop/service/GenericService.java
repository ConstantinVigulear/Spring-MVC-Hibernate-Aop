package com.vigulear.spring.mvc_hibernate_aop.service;

import java.util.List;
import java.util.UUID;

public interface GenericService<T> {

    T persist(T entity);

    T update(T entity);

    T findById(Long id);

    List<T> findAll();

    void delete(T entity);

    void deleteById(Long id);
}
