package dbms.hibernate;

import core.collection.benchmark.utils.MaxUtils;
import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import utils.IndentUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.ClassUtils.simpleName;
import static utils.ConcurrencyUtils.syncPrintfln;
import static utils.ConcurrencyUtils.syncPrintln;
import static utils.PrintUtils.printfln;

public class HibernateUtils {

    public static final String DELIMITER = "  ";
    public static final String LINE_SYMBOL = "-";
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(String resourceName, Class<?>... entitiesClasses) {
        if(sessionFactory == null) createSessionFactory(resourceName, entitiesClasses);
        return sessionFactory;
    }

    private static void createSessionFactory(String resourceName, Class<?> ... entitiesClasses) {

        try {
            registry = new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build();

            MetadataSources metadataSources = new MetadataSources(registry);
            Arrays.stream(entitiesClasses)
                .forEach(metadataSources::addAnnotatedClass);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

    public static SessionFactory getSessionFactory(
        int port,
        String database,
        String user, String password, String schema,
        Class<?>... annotatedClasses
    ) {
        ProviderProperties providerProperties = new ProviderPropertiesBuilder()
            .port(port)
            .databaseName(database)
            .userName(user)
            .password(password)
            .schema(schema)
            .build();

        ServiceRegistry serviceRegistry =
            new StandardServiceRegistryBuilder()
                .applySettings(providerProperties.getProperties())
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);


        return metadataSources
            .buildMetadata()
            .getSessionFactoryBuilder()
            .build();
    }

    public static <T> List<T> findAll(Session session, Class<T> type) {
        CriteriaQuery<T> selectFrom = selectFrom(session, type);
        TypedQuery<T> allQuery = session.createQuery(selectFrom);
        return allQuery.getResultList();
    }

    public static <T, Id extends Serializable> Optional<T> find(Session session, Class<T> type, Id id) {
        return Optional.ofNullable(session.get(type, id));
    }

    private static <T> CriteriaQuery<T> selectFrom(Session session, Class<T> type) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> from = criteriaQuery.from(type);
        return criteriaQuery.select(from);
    }

    public static <T> Long count(Session session, Class<T> type) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);

        cr.select(cb.count(cr.from(type)));
        Query<Long> query = session.createQuery(cr);
        List<Long> itemProjected = query.getResultList();
        return itemProjected.get(0);
    }

    public static Session reopen(Session session, SessionFactory sessionFactory) {
        session.close();
        return sessionFactory.openSession();
    }

    public static <T> void printJoin(
        Session session,
        String title,
        String queryString,
        Function<T, Comparable> sorting,
        List<Function<T, ?>> getters
    ) {
        Query query = session.createQuery(queryString);
        List<T> list = query.list();
        list.sort(Comparator.comparing(sorting));

        String[][] values = new String[list.size()][getters.size()];

        String[] longestsColumnsValues = new String[getters.size()];
        for (var ref = new Object() { int i = 0; }; ref.i < longestsColumnsValues.length; ref.i++) {
            List<?> columnValues = list.stream()
                .map(result -> getters.get(ref.i).apply(result))
                .collect(Collectors.toList());

            String longest = MaxUtils.longest(columnValues);
            longestsColumnsValues[ref.i] = longest;
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < getters.size(); j++) {
                T result = list.get(i);
                Function<T, ?> getter = getters.get(j);
                String field = getter.apply(result) == null ? "null": getter.apply(result).toString();
                String fieldWithIndent = IndentUtils.addIndent(field, longestsColumnsValues[j].length());
                values[i][j] = fieldWithIndent;
            }
        }

        printTable(title, values);
    }

    public static <T> void printNativeJoin(Session session, String queryString) {
        printNativeJoin(session, "", queryString);
    }

    public static <T> void printNativeJoin(
        Session session,
        String title,
        String queryString
    ) {
        Query query = session.createSQLQuery(queryString);
        List<Object[]> list = query.list();

        int fieldsAmount = list.get(0).length;

        String[][] values = new String[list.size()][fieldsAmount];

        String[] longestsColumnsValues = new String[fieldsAmount];
        for (var ref = new Object() { int i = 0; }; ref.i < longestsColumnsValues.length; ref.i++) {
            List<?> columnValues = list.stream()
                .map(result -> result[ref.i])
                .collect(Collectors.toList());

            String longest = MaxUtils.longest(columnValues);
            longestsColumnsValues[ref.i] = longest;
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < fieldsAmount; j++) {
                Object[] result = list.get(i);
                String field = result[j] == null ? "null" : result[j].toString();
                String fieldWithIndent = IndentUtils.addIndent(field, longestsColumnsValues[j].length());
                values[i][j] = fieldWithIndent;
            }
        }

        printTable(title, values);
    }

    private static void printTable(String title, String[][] values) {
        if (values.length == 0) {
            syncPrintln(LINE_SYMBOL.repeat(20));
            syncPrintln(title);
            syncPrintln(LINE_SYMBOL.repeat(20));
            return;
        };


        String template = template(values[0].length);
        Optional<Integer> reduced = Arrays.stream(values[0]).map(String::length).reduce(Integer::sum);
        Integer lineLength = reduced.get() + 2 + values.length;
        syncPrintln(LINE_SYMBOL.repeat(lineLength));
        syncPrintln(title);

        for (String[] value : values) {
            syncPrintfln(template, value);
        }

        syncPrintln(LINE_SYMBOL.repeat(lineLength));
    }

    private static String template(int fieldsAmount) {
        return StringUtils.joinWith(DELIMITER, Collections.nCopies(fieldsAmount, "%s"));
    }

    public static Runnable throwNotFound(AutoGeneratedId<?> entity) {
        return throwNotFound(simpleName(entity), entity.getId());
    }

    public static Runnable throwNotFound(String simpleName, Serializable id) {
        String template = "%s (id = '%s') was not found in database";
        return () -> {
            throw new RuntimeException(String.format(template, simpleName, id));
        };
    }

    public static Runnable throwNotFound(String fieldValue, Class<?> entityClass) {
        return () -> {
            throw new RuntimeException(entityClass.getSimpleName() + " (" + fieldValue + ") was not found");
        };
    }

    public static void printlnRemove(AutoGeneratedId<?> entity) {
        printfln("%s (id = '%s') was removed", simpleName(entity), entity.getId());
    }
}
