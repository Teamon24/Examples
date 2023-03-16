package dbms.hibernate;

import org.hibernate.Session;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionUtils {

    public static void persist(Session session, List<?> entities) {
        commit(Session::persist, session, entities);
    }

    public static void persist(Session session, Object... entities) {
        commit(Session::persist, session, entities);
    }

    public static void save(Session session, List<?> entities) {
        commit(Session::save, session, entities);
    }

    public static <Entity> List<Entity> save(Session session, Entity... entities) {
        return commit(Session::save, session, entities);
    }

    public static void refresh(Session session, List<?> entities) {
        commit(Session::refresh, session, entities);
    }

    public static void refresh(Session session, Object... entities) {
        commit(Session::refresh, session, entities);
    }

    public static <Entity> List<Entity> commit(
        BiConsumer<Session, Entity> operation,
        Session session,
        Entity... entities
    ) {
        commit(session, (s) -> {
            Arrays.stream(entities).forEach(it -> operation.accept(session, it));
        });
        return Arrays.stream(entities).collect(Collectors.toList());
    }

    public static <Entity> void commit(
        BiConsumer<Session, Entity> operation,
        Session session,
        Collection<Entity> entities
    ) {
        commit(session, (s) -> {
            entities.forEach(it -> operation.accept(session, it));
        });
    }

    public static void commit(Session session, Consumer<Session> block) {
        session.getTransaction().begin();
        block.accept(session);
        session.getTransaction().commit();
    }

    public static <Entity> List<Entity> commit(Session session, Function<Session, List<Entity>> block) {
        session.getTransaction().begin();
        List<Entity> entities = block.apply(session);
        session.getTransaction().commit();
        return entities;
    }

    public static <Entity> List<Entity> operate(
        Session session,
        BiConsumer<Session, Entity> operation,
        Entity... entities
    ) {
        Arrays.stream(entities).forEach(it -> operation.accept(session, it));
        return Arrays.asList(entities);
    }

    public static <Entity> void operate(
        Session session,
        BiConsumer<Session, Entity> operation,
        Collection<Entity> entities
    ) {
        entities.forEach(it -> operation.accept(session, it));
    }
}
