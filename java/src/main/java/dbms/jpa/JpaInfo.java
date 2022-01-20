package dbms.jpa;

/**
 * <ul><a href="https:/www.tutorialspoint.com/jpa"><strong>www.tutorialspoint.com/jpa</strong></a>:
 *     <a href="https://www.tutorialspoint.com/jpa/jpa_architecture.htm">
 *     Jpa architecture</a></ul>
 * <ul><a href="https://www.baeldung.com"><strong>www.baeldung.com</strong></a>:
 *     <a href="https://www.baeldung.com/cs/transactions-intro">
 *     Introducing transaction</a></ul>
 * <ul><a href="https://en.wikibooks.org/wiki/Java_Persistence"><strong>en.wikibooks.org/wiki/Java_Persistence</strong></a>:
 *     <a href="https://en.wikibooks.org/wiki/Java_Persistence/Caching#Object_Identity">
 *     Java Persistence Cachin: Object Identity</a></ul>
 * <ul><a href="https://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html_single/"><strong>docs.jboss.org/hibernate/stable/entitymanager</strong></a>:
 *     <a href="https://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html_single/#transactions-basics-uow">
 *     Chapter 5. Transactions and Concurrency</a></ul>
 */
class JpaInfo {
    public static void main(String[] args) {

        //Hibernate-реализация jpa-интерфейсов.
        org.hibernate.SessionFactory sessionFactory = null;
        org.hibernate.Session session = null;
        org.hibernate.Transaction transaction = null;
        org.hibernate.query.Query hibernateQuery = null;
        org.hibernate.jpa.internal.PersistenceUnitUtilImpl persistenceUnitUtil = null;

        //Интерфейсы, предоставляемые спецификацией JPA.
        javax.persistence.EntityManagerFactory entityManagerFactory = sessionFactory;
        javax.persistence.EntityManager entityManager = session;
        javax.persistence.EntityTransaction entityTransaction = transaction;
        javax.persistence.Query jpaQuery = hibernateQuery;
        javax.persistence.Persistence persistence;
        javax.persistence.PersistenceUnitUtil persistenceUnitUtil1 = persistenceUnitUtil;
    }
}

