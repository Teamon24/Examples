package dbms.jpa;

/**
 * <p>Persistence Context:
 * <ul>
 * <li>Entities are managed by javax.persistence.EntityManager instance using <strong><i>persistence context</i></strong>.</li>
 * <li>Each EntityManager instance is associated with a <strong><i>persistence context</i></strong>.</li>
 * <li>Within the <strong><i>persistence context</i></strong>, the entity instances and their lifecycle are managed.</li>
 * <li><strong><i>Persistence context</i></strong> defines a scope under which particular entity instances are created, persisted, and removed.</li>
 * <li>A <strong><i>persistence context</i></strong> is like a cache which contains a set of persistent entities, so once the transaction is finished, all persistent objects are detached from the EntityManager's persistence context and are no longer managed.</li>
 * </ul>
 */
public class PersistenceContextInfo {}
