package dbms.hibernate.cascade;

import com.github.javafaker.Faker;
import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import dbms.hibernate.TransactionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.ExceptionUtils;
import utils.ListGenerator;

import java.util.Optional;

public interface ManyToMany {

    Logger logger = Logger.getLogger(ManyToMany.class);

    String DAY_DREAMING_2ND_EDITION = "Day Dreaming, Second Edition";
    String MARK_ARMSTRONG = "Mark Armstrong";
    String JOHN_SMITH = "John Smith";
    String MICHELLE_DIANGELLO = "Michelle Diangello";

    String LEFT_JOIN_BOOKS_TO_AUTHORS =
        "select new dbms.hibernate.cascade.AuthorNameAndBookTitle(a.id, a.fullName, b.title) " +
        "from Author a " +
        "left join a.books b";

    static void main(String[] args) {

        logger.info("Application was started");

        SessionFactory sessionFactory = new SessionFactoryBuilder()
            .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
            .entitiesClasses(
                Author.class,
                AuthorCascadeAll.class,
                AuthorAndBookCascadeAll.class,
                Book.class,
                BookAndAuthorCascadeAll.class
            ).build();

        Session session = sessionFactory.openSession();

        cascadePersist(session);
        disassociatingDelete(session);
        deleteCascadeAllByAuthor(session);

        session.clear();
        deleteCascadeAllByAuthorAndBook(session);

    }

    private static void cascadePersist(Session session) {
        TransactionUtils.commit(session, ManyToMany::persistInitialData);
        printJoin(session, "cascadePersist");
    }

    private static void persistInitialData(Session session) {
        Book dayDreaming = new Book("Day Dreaming");
        Book dayDreamingEdition2 = new Book(DAY_DREAMING_2ND_EDITION);
        Faker faker = Faker.instance();

        TransactionUtils.operate(session,
            Session::persist,
            new Author(JOHN_SMITH).addBooks(dayDreaming, dayDreamingEdition2),
            new Author(MICHELLE_DIANGELLO).addBooks(dayDreaming, dayDreamingEdition2),
            new Author(MARK_ARMSTRONG)
                .addBook(dayDreamingEdition2)
                .addBooks(ListGenerator.create(10, () -> new Book(faker.book().title())))
        );
    }

    private static void disassociatingDelete(Session session) {
        TransactionUtils.commit(session, s -> {
            findMarkArmstrong(session, Author.class).ifPresentOrElse(
                markArmstrong -> session.delete(markArmstrong.removeBooks()),
                HibernateUtils.throwNotFound(MARK_ARMSTRONG, Author.class)
            );
        });

        TransactionUtils.commit(session, s -> {
            findJohnSmith(session, AuthorAndBookCascadeAll.class).ifPresentOrElse(
                johnSmith -> throwIfBooksAmountsAreDifferent(johnSmith, 2),
                HibernateUtils.throwNotFound(JOHN_SMITH, Author.class)
            );
        });

        printJoin(session, "Disassociating delete");
        restoreData(session);
    }

    private static void throwIfBooksAmountsAreDifferent(AuthorEssential author, int expectedBooksAmount) {
        ExceptionUtils.throwIf(
            expectedBooksAmount != author.getBooksAmount(),
            String.format("%s should has %s book(s)", author.fullName, expectedBooksAmount));
    }

    private static void deleteCascadeAllByAuthor(Session session) {
        TransactionUtils.commit(session, s -> {
            findMarkArmstrong(session, AuthorCascadeAll.class).ifPresent(session::delete);
        });

        TransactionUtils.commit(session, s -> {
            findJohnSmith(session, AuthorCascadeAll.class)
                .ifPresentOrElse(
                    johnSmith -> throwIfBooksAmountsAreDifferent(johnSmith, 1),
                    HibernateUtils.throwNotFound(JOHN_SMITH, Author.class)
                );
        });

        printJoin(session, "delete Cascade.All: Author");
        restoreData(session);
    }

    private static void deleteCascadeAllByAuthorAndBook(Session session) {
        TransactionUtils.commit(session, s -> {
            findMarkArmstrong(s, AuthorAndBookCascadeAll.class).ifPresent(s::remove);
        });

        TransactionUtils.commit(session, s -> {
            findJohnSmith(s, AuthorAndBookCascadeAll.class).ifPresent(author -> {
                throw new RuntimeException(author.fullName + " should be deleted");
            });
        });

        printJoin(session, "delete Cascade.All: Author and Book");
        restoreData(session);
    }

    private static void restoreData(Session session) {
        TransactionUtils.commit(session, s -> {
            s.createNativeQuery("truncate table examples.books_authors cascade").executeUpdate();
            s.createNativeQuery("truncate table examples.books cascade").executeUpdate();
            s.createNativeQuery("truncate table examples.authors cascade").executeUpdate();
        });

        TransactionUtils.commit(session, ManyToMany::persistInitialData);
        printJoin(session, "restored database");
    }

    private static <A extends AuthorEssential> Optional<A> findMarkArmstrong(Session session, Class<A> authorClass) {
        return ExampleUtils.findAuthorByName(session, authorClass, MARK_ARMSTRONG).flatMap(it -> {
            session.refresh(it);
            return Optional.of(it);
        });
    }

    private static <A extends AuthorEssential> Optional<A> findJohnSmith(Session session, Class<A> authorClass) {
        return ExampleUtils.findAuthorByName(session, authorClass, JOHN_SMITH).flatMap(it -> {
            session.refresh(it);
            return Optional.of(it);
        });
    }

    private static void printJoin(Session session, String title) {
        HibernateUtils.printJoin(
            session,
            title,
            LEFT_JOIN_BOOKS_TO_AUTHORS,
            AuthorNameAndBookTitle::getAuthorId,
            AuthorNameAndBookTitle.getters());
    }
}
