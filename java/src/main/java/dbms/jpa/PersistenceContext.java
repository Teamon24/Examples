package dbms.jpa;

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Persistence Context</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 *
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
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Unit of work</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>The persistence context is an implementation of the Unit of Work pattern. It keeps track of all loaded data, tracks changes of that data, and is responsible to eventually synchronize any changes back to the database at the end of the business transaction.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Identity Map</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>JPA persistence context is a good example of the Identity Map Pattern. In JPA entity identity is maintained within a transaction and within an entity manager. For example:
 * <pre>{@code Employee employee1 = entityManager.find(Employee.class, 123);
 * Employee employee2 = entityManager.find(Employee.class, 123);
 * assert (employee1 == employee2);}</pre>
 * <ul><i>Assert expression will return true. The second call will get the entity from the persistence context, without querying the database.</i></ul>
 * <ul><i>This holds true no matter how the object is accessed:</i></ul>
 * <pre>{@code Employee employee1 = entityManager.find(Employee.class, 123);
 * Employee employee2 = employee1.getManagedEmployees().get(0).getManager();
 * assert (employee1 == employee2);}</pre>
 * In JPA, object identity is not maintained across EntityManagers. Each EntityManager maintains its own persistence context, and its own transactional state of its objects. So the following is true in JPA:
 *  <pre>{@code
 *  EntityManager entityManager1 = factory.createEntityManager();
 *  EntityManager entityManager2 = factory.createEntityManager();
 *  Employee employee1 = entityManager1.find(Employee.class, 123);
 *  Employee employee2 = entityManager2.find(Employee.class, 123);
 *  assert (employee1 != employee2);}</pre>
 * <p>From the EntityManager javadoc:
 * <ul>A persistence context is a set of entity instances in which for any persistent entity identity there is a unique entity instance. Within the persistence context, the entity instances and their lifecycle are managed. The EntityManager API is used to create and remove persistent entity instances, to find entities by their primary key, and to query over entities.</ul>

 */
public interface PersistenceContext {
}
