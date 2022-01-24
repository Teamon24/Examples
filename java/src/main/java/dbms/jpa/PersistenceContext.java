package dbms.jpa;

/**
 * <p>Persistence Context:
 * <ul>
 *     <li>A <strong><i>persistence context</i></strong> is a set of entity instances in which for any persistent entity identity there is a unique entity instance.</li>
 *     <li>Entities are managed by <i>EntityManager</i> instance using <strong><i>persistence context</i></strong>.</li>
 *     <li>Each <i>EntityManager</i> instance is associated with a <strong><i>persistence context</i></strong>.</li>
 *     <li>Persistence contexts are available in two types: (<a href="https://www.baeldung.com/jpa-hibernate-persistence-context#persistence_context_type">persistence context type</a>)
 *          <ul>
 *          <li>Transaction-scoped persistence context</li>
 *          <li>Extended-scoped persistence context</li>
 *          </ul>
 *          </li>
 *     <li>Within the <strong><i>persistence context</i></strong>, the entity instances and their lifecycle are managed.</li>
 *     <li><strong><i>Persistence context</i></strong> defines a scope under which particular entity instances are created, persisted, and removed.</li>
 *      <li><strong><i>The persistence context</i></strong> is an implementation of the <i>Unit of Work</i> pattern. It keeps track of all loaded data, tracks changes of that data, and is responsible to eventually synchronize any changes back to the database at the end of the business transaction.</li>
 *     <li>A <strong><i>persistence context</i></strong> is like a cache which contains a set of persistent entities, so once the transaction is finished, all persistent objects are detached from the <i>EntityManager's</i> persistence context and are no longer managed.</li>
 * <li>JPA also implements something like an <i>Identity Map</i> pattern in the first level cache. It saves a map of entities in its first level cache avoiding going to look for more than one time to the database for the duration of the session and the unit of work.</li>
 * </ul>

 */
public interface PersistenceContext {
}
