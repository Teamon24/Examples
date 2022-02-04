package dbms.hibernate.cascade;

/**
 * <pre>
 * |-------------------------------------|-----------------|
 * | JPA ENTITYMANAGER ACTION            | CASCADETYPE     |
 * |-------------------------------------|-----------------|
 * | detach(entity)                      | DETACH          |
 * | merge(entity)                       | MERGE           |
 * | persist(entity)                     | PERSIST         |
 * | refresh(entity)                     | REFRESH         |
 * | remove(entity)                      | REMOVE          |
 * |                                     |                 |
 * |                                     |                 |
 * | lock(entity, lockModeType)          |                 |
 * | All the above EntityManager methods | ALL             |
 * |-------------------------------------|-----------------|
 *
 * |-----------------------------------------|----------------------|
 * | HIBERNATE SESSION ACTION                |CASCADETYPE           |
 * |-----------------------------------------|----------------------|
 * | evict(entity)                           |DETACH or EVICT       |
 * | merge(entity)                           |MERGE                 |
 * | persist(entity)                         |PERSIST               |
 * | refresh(entity)                         |REFRESH               |
 * | delete(entity)                          |REMOVE or DELETE      |
 * | saveOrUpdate(entity)                    |SAVE_UPDATE           |
 * | replicate(entity, replicationMode)      |REPLICATE             |
 * | buildLockRequest(entity, lockOptions)   |LOCK                  |
 * | All the above Hibernate Session methods |ALL                   |
 * |-----------------------------------------|----------------------|
 *
 *
 * </pre>
 */
public interface Types {
}
