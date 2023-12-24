/**
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Examples</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
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
package architecture.patterns.enterprise.identity_map;