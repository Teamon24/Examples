/**
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Transient</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>The lifecycle state of a newly instantiated entity object is called transient. The entity hasn’t been persisted yet, so it doesn’t represent any database record.
 *
 * <p>Your persistence context doesn’t know about your newly instantiate object. Because of that, it doesn’t automatically perform an SQL INSERT statement or tracks any changes. As long as your entity object is in the lifecycle state transient, you can think of it as a basic Java object without any connection to the database and any JPA-specific functionality.
 * <pre>{@code
 * Author author = new Author();
 * author.setFirstName("Thorben");
 * author.setLastName("Janssen");}</pre>
 * <p>That changes when you provide it to the EntityManager.find method. The entity object then changes its lifecycle state to managed and gets attached to the current persistence context.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Managed</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>All entity objects attached to the current persistence context are in the lifecycle state managed. That means that your persistence provider, e.g. Hibernate, will detect any changes on the objects and generate the required SQL INSERT or UPDATE statements when it flushes the persistence context.
 * <p>There are different ways to get an entity to the lifecycle state managed:
 * <ul>
 * <li>You can call the EntityManager.persist method with a new entity object.</li>
 * <pre>{@code
 * Author author = new Author();
 * author.setFirstName("Thorben");
 * author.setLastName("Janssen");
 * em.persist(author);}</pre>
 * <li>You can load an entity object from the database using the EntityManager.find method, a JPQL query, a CriteriaQuery, or a native SQL query.</li>
 * <pre>{@code Author author = em.find(Author.class, 1L);}</pre>
 * <li>You can merge a detached entity by calling the EntityManager.merge method or update it by calling the update method on your Hibernate Session.</li>
 * </ul>
 * <pre>{@code em.merge(author);}</pre>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Detached</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>An entity that was previously managed but is no longer attached to the current persistence context is in the lifecycle state detached.
 * <p>An entity gets detached when you close the persistence context. That typically happens after a request got processed. Then the database transaction gets committed, the persistence context gets closed, and the entity object gets returned to the caller. The caller then retrieves an entity object in the lifecycle state detached.
 * Y<p>ou can also programmatically detach an entity by calling the detach method on the EntityManager.
 * <pre>{@code em.detach(author);}</pre>
 * <p>There are only very few performance tuning reasons to detach a managed entity. If you decide to detach an entity, you should first flush the persistence context to avoid losing any pending changes.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Reattaching an entity</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>You can reattach an entity by calling the update method on your Hibernate Session or the merge method on the EntityManager. There are a few subtle differences between these operations that I explain in great detail in What’s the difference between persist, save, merge and update? Which one should you use?
 * <p>In both cases, the entity changes its lifecycle state to managed.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Removed</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>When you call the remove method on your EntityManager, the mapped database record doesn’t get removed immediately. The entity object only changes its lifecycle state to removed.
 * <p>During the next flush operation, Hibernate will generate an SQL DELETE statement to remove the record from the database table.
 * <pre>{@code em.remove(author); }</pre>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Conclusion</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>All entity operations are based on JPA’s lifecycle model. It consists of 4 states, which define how your persistence provider handles the entity object.
 * <p>New entities that are not attached to the current persistence context are in the transient state.
 * <p>If you call the persist method on the EntityManager with a new entity object or read an existing record from the database, the entity object is in the managed state. It’s connected to the current persistence context. Your persistence context will generate the required SQL INSERT and UPDATE statement to persist the current state of the object.
 * <p>Entities in the state removed are scheduled for removal. The persistence provider will generate and execute the required SQL DELETE statement during the next flush operation.
 * <p>If a previously managed entity is no longer associated with an active persistence context, it has the lifecycle state detached. Changes to such an entity object will not be persisted in the database.
 */
package dbms.jpa.life_cycle;