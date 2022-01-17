package jpa.hibernate.ex1;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class DAO<K, T extends Serializable> {

    private final EntityManager entityManager;
    private final Class<T> entityClass;


   public DAO(EntityManager entityManager, Class<T> entityClass) {
      this.entityManager = entityManager;
      this.entityClass = entityClass;
   }

   public T findOne(K id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return (List<T>) entityManager.createQuery("from " + entityClass.getName()).getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(K id) {
        T entity = findOne(id);
        delete(entity);
    }
}