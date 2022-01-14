package jpa.hiberbate;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class DAO<K, T extends Serializable> {

    private final EntityManager entityManager;
    private final Class<T> clazz;


   public DAO(EntityManager entityManager, Class<T> clazz) {
      this.entityManager = entityManager;
      this.clazz = clazz;
   }

   public T findOne(K id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        return (List<T>) entityManager.createQuery("from " + clazz.getName()).getResultList();
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