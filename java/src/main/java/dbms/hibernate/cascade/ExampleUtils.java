package dbms.hibernate.cascade;

import org.hibernate.Session;

import java.util.List;

public class ExampleUtils {
    public static <A extends AuthorEssential> A findAuthorByName(Session session, Class<A> authorClass, String name) {
        List<?> entities = session
            .createQuery(String.format("select author from %s author where author.fullName = :fullName", authorClass.getSimpleName()))
            .setParameter("fullName", name).getResultList();

        return (A) entities.get(0);
    }

    public static <B extends BookEssential> B findBookByTitle(Session session, Class<B> bookClass, String title) {
        List<?> entities = session
            .createQuery(String.format("select book from %s book where book.title = :title", bookClass.getSimpleName()))
            .setParameter("title", title).getResultList();

        return (B) entities.get(0);
    }
}
