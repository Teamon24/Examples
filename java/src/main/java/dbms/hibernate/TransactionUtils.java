package dbms.hibernate;

import org.hibernate.Session;
import utils.Voider;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TransactionUtils {

    public static <T> T commit(Session session, Supplier<T> supplier) {
        session.getTransaction().begin();
        T t = supplier.get();
        session.getTransaction().commit();
        return t;
    }

    public static void commit(Session session, Consumer<Session> block) {
        session.getTransaction().begin();
        block.accept(session);
        session.getTransaction().commit();
    }

    public static <T> T commit(Session session, Function<Session, T> block) {
        session.getTransaction().begin();
        T result = block.apply(session);
        session.getTransaction().commit();
        return result;
    }

    public static void commit(Session session, Voider block) {
        session.getTransaction().begin();
        block.invoke();
        session.getTransaction().commit();
    }
}
