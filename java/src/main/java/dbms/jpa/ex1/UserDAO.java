package dbms.jpa.ex1;

import javax.persistence.EntityManager;

public class UserDAO extends DAO<String, UserEntity> {
    public UserDAO(EntityManager entityManager) {
        super(entityManager, UserEntity.class);
    }
}
