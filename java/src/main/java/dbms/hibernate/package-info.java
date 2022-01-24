/**
 *
 * <ul><a href="https://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html_single/">
 *     <strong>docs.jboss.org/hibernate/stable/entitymanager</strong></a></ul>
 *
 * <ul><a href="https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate">
 *     <strong>hibernate: save, persist, update, merge, saveOrUpdate</strong></a></ul>
 * <ul><a href="https://www.baeldung.com/learn-jpa-hibernate">
 *     <strong>baeldung: learn jpa hibernate</strong></a></ul>
 *
 *
 *     <pre>{@code
 *
 *         //Hibernate-реализация jpa-интерфейсов.
 *         org.hibernate.SessionFactory sessionFactory = null;
 *         org.hibernate.Session session = null;
 *         org.hibernate.Transaction transaction = null;
 *         org.hibernate.query.Query hibernateQuery = null;
 *         org.hibernate.jpa.internal.PersistenceUnitUtilImpl persistenceUnitUtil = null;
 *
 *         //Интерфейсы, предоставляемые спецификацией JPA.
 *         javax.persistence.EntityManagerFactory entityManagerFactory = sessionFactory;
 *         javax.persistence.EntityManager entityManager = session;
 *         javax.persistence.EntityTransaction entityTransaction = transaction;
 *         javax.persistence.Query jpaQuery = hibernateQuery;
 *         javax.persistence.Persistence persistence;
 *         javax.persistence.PersistenceUnitUtil persistenceUnitUtil1 = persistenceUnitUtil;
 * }</pre>
 *
 */
package dbms.hibernate;


