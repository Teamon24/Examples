package dbms.hibernate.cascade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Getter
@AllArgsConstructor
class AuthorNameAndBookTitle {

    private Integer authorId;
    private String fullName;
    private String title;

    public static List<Function<AuthorNameAndBookTitle, ?>> getters() {
        return List.of(
            AuthorNameAndBookTitle::getAuthorId,
            AuthorNameAndBookTitle::getFullName,
            AuthorNameAndBookTitle::getTitle
        );
    }
}

public class ExampleUtils {

    public static <A extends AuthorEssential> Optional<A> findAuthorByName(Session session, Class<A> authorClass, String name) {
        Query fullName = session
            .createQuery(String.format("select author from %s author where author.fullName = :fullName", authorClass.getSimpleName()))
            .setParameter("fullName", name);

        List<?> entities = fullName
            .getResultList();

        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of((A) entities.get(0));
        }
    }

    public static <B extends BookEssential> Optional<B> findBookByTitle(Session session, Class<B> bookClass, String title) {
        List<?> entities = session
            .createQuery(String.format("select book from %s book where book.title = :title", bookClass.getSimpleName()))
            .setParameter("title", title).getResultList();

        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of((B) entities.get(0));
        }
    }


}
