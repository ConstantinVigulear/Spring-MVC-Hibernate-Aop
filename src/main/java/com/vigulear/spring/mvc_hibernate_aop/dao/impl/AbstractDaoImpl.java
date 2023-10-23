package com.vigulear.spring.mvc_hibernate_aop.dao.impl;

import com.vigulear.spring.mvc_hibernate_aop.dao.GenericDao;
import com.vigulear.spring.mvc_hibernate_aop.aspect.exception_handling.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import jakarta.persistence.Query;

public abstract class AbstractDaoImpl<T> implements GenericDao<T> {

  @PersistenceContext private EntityManager entityManager;

  protected abstract Class<T> getEntityClass();

  @Override
  @SuppressWarnings("unchecked")
  public T findById(Long id) {
    Query query =
            entityManager.createQuery("FROM " + getEntityClass().getName() + " e WHERE e.id = :id");
    query.setParameter("id", id);

    T entity = (T) query.getSingleResult();

    if (entity == null) {
      throw new NotFoundException(
              "Delete error: entity with id = " + id + "not found and cannot be deleted");
    }

    return entity;
  }

  @Override
  public List<T> findAll() {
    return entityManager
        .createQuery("FROM " + getEntityClass().getName(), getEntityClass())
        .getResultList();
  }

  @Override
  public void delete(T t) {
    entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
  }

  @Override
  public void deleteById(Long id) {

    T entity = findById(id);

    if (entity == null) {
      throw new NotFoundException(
          "Delete error: entity with id = " + id + "not found and cannot be deleted");
    }

    this.delete(entity);
  }
}
