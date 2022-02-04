package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import nl.jqno.equalsverifier.internal.util.Formatter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static dbms.hibernate.TransactionUtils.commit;
import static nl.jqno.equalsverifier.internal.util.Assert.assertTrue;

public interface ManyToMany {

    String DAY_DREAMING_2ND_EDITION = "Day Dreaming, Second Edition";
    String MARK_ARMSTRONG = "Mark Armstrong";
    String JOHN_SMITH = "John Smith";

    static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(
            "/META-INF/hibernate-postgresql-example.cfg.xml",
            Author.class, Book.class);
        Session session = sessionFactory.openSession();

        cascadePersist(session);
        disassociatingDelete(session);
        cascadeAllDelete(session);
    }

    private static void cascadePersist(Session session) {
        Author johnSmith = new Author(JOHN_SMITH);
        Author michelleDiangello = new Author("Michelle Diangello");
        Author markArmstrong = new Author(MARK_ARMSTRONG);

        Book dayDreaming = new Book("Day Dreaming");
        Book dayDreamingEdition2 = new Book(DAY_DREAMING_2ND_EDITION);

        johnSmith.addBook(dayDreaming);
        johnSmith.addBook(dayDreamingEdition2);

        michelleDiangello.addBook(dayDreaming);
        michelleDiangello.addBook(dayDreamingEdition2);

        markArmstrong.addBook(dayDreamingEdition2);

        session.persist(johnSmith);
        session.persist(michelleDiangello);
        session.persist(markArmstrong);
    }

    private static void disassociatingDelete(Session session) {
        commit(session, s -> {
            Author markArmstrong = ExampleUtils.findAuthorByName(session, Author.class, MARK_ARMSTRONG);
            markArmstrong.removeBooks();
            session.delete(markArmstrong);
            Author johnSmith = ExampleUtils.findAuthorByName(session, Author.class, JOHN_SMITH);
            assertTrue(Formatter.of(""), 2 == johnSmith.getBooks().size());
        });
    }

    private static void cascadeAllDelete(Session session) {
        commit(session, s -> {
            Book book2 = ExampleUtils.findBookByTitle(s, Book.class, DAY_DREAMING_2ND_EDITION);
            BookCascadeAll book = ExampleUtils.findBookByTitle(s, BookCascadeAll.class, DAY_DREAMING_2ND_EDITION);
            AuthorCascadeAll newAuthor = new AuthorCascadeAll(MARK_ARMSTRONG);
            newAuthor.addBook(book);
            s.save(newAuthor);
        });

        commit(session, s -> {
            AuthorCascadeAll markArmstrong = ExampleUtils.findAuthorByName(session, AuthorCascadeAll.class, MARK_ARMSTRONG);
            session.delete(markArmstrong);
            AuthorCascadeAll johnSmith = ExampleUtils.findAuthorByName(session, AuthorCascadeAll.class, JOHN_SMITH);
            assertTrue(Formatter.of(""), 1 == johnSmith.getBooks().size());
        });
    }
}
