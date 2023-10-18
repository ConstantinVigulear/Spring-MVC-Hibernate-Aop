package com.vigulear.spring.mvc_hibernate_aop.dao;

import java.util.List;

public interface GenericDao<T> {

  T findById(Long id);

  List<T> findAll();

  T persist(T t);

  T update(T t);

  void delete(T t);

  void deleteById(Long id);
}
