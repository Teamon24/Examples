package dbms.hibernate;

import com.google.common.collect.Lists;
import core.collection.benchmark.utils.MaxUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import utils.ClassUtils;
import utils.IndentUtils;
import utils.NullUtils;
import utils.PrintUtils;

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

import static org.apache.commons.lang3.StringUtils.SPACE;
import static utils.ConcurrencyUtils.syncPrintln;

public class HibernateUtils {

    public static final String DELIMITER = "  ";
    public static final String LINE_SYMBOL = "-";

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

    public static void printNativeSelect(Session session, String title, String queryString) {
        printNativeSelect(session, title, queryString, true);
    }

    public static void printNativeSelect(Session session, String queryString) {
        printNativeSelect(session, "", queryString, true);
    }

    public static Pair<String, String[][]> getNativeSelectResult(Session session, String queryString) {
        return getNativeSelectResult(session, "", queryString, true);
    }

    public static void printNativeSelectWithColumns(Session session, String title, String queryString) {
        printNativeSelect(session, title, queryString, false);
    }

    public static void printNativeSelectWithColumns(Session session, String queryString) {
        printNativeSelect(session, "", queryString, false);
    }

    public static void printNativeSelect(
        Session session,
        String title,
        String queryString,
        Boolean noColumnsTitle
    ) {
        String[][] values = createNativeSelectResult(session, title, queryString, noColumnsTitle);
        if (values == null) return;

        printTable(title, values);
    }

    public static Pair<String, String[][]> getNativeSelectResult(
        Session session,
        String title,
        String queryString,
        Boolean noColumnsTitle
    ) {
        String[][] values = createNativeSelectResult(session, title, queryString, noColumnsTitle);
        if (values == null) return null;
        return Pair.of(title, values);
    }

    private static String[][] createNativeSelectResult(Session session, String title, String queryString, Boolean noColumnsTitle) {
        Query query = session.createSQLQuery(queryString);
        List<Object[]> list = query.list();

        List<String> columnsTitles = getColumnsTitles(noColumnsTitle, queryString);

        if (list.size() == 0) {
            printEmptyTable(title, columnsTitles);
            return null;
        }

        int fieldsAmount = list.get(0).length;

        if (!noColumnsTitle && columnsTitles.size() != fieldsAmount)
            throw new RuntimeException("Column titles amount should be equal to result fields amount");

        if (!noColumnsTitle) list.add(0, columnsTitles.toArray());

        String[][] values = new String[list.size()][fieldsAmount];

        String[] longestsColumnsValues = new String[fieldsAmount];
        for (var ref = new Object() { int i = 0; }; ref.i < longestsColumnsValues.length; ref.i++) {
            List<?> columnValues = list.stream()
                .map(result -> NullUtils.get("null", result[ref.i]))
                .collect(Collectors.toList());

            String longest = MaxUtils.longest(columnValues);
            longestsColumnsValues[ref.i] = longest;
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < fieldsAmount; j++) {
                Object[] result = list.get(i);
                String field = NullUtils.get("null", result[j]).toString();
                String fieldWithIndent = IndentUtils.addIndent(field, longestsColumnsValues[j].length());
                values[i][j] = fieldWithIndent;
            }
        }
        return values;
    }

    private static void printEmptyTable(String title, List<String> columnsTitles) {
        syncPrintln(() -> {
            System.out.println(LINE_SYMBOL.repeat(20));
            System.out.println(StringUtils.joinWith("|", columnsTitles));
            System.out.println(title);
            System.out.println(LINE_SYMBOL.repeat(20));
        });
    }

    private static List<String> getColumnsTitles(Boolean noColumnsTitle, String queryString) {
        if (noColumnsTitle) return Lists.newArrayList();

        List<String> selectSplit = splitNotBlank(queryString, "select");
        List<String> columnsTitles = splitNotBlank(selectSplit.get(0), "from");
        List<String> strings = splitNotBlank(columnsTitles.get(0), ",");
        List<String> collect = strings.stream().map(it -> {
            List<String> split = splitNotBlank(it, SPACE);
            if (split.size() == 1) return split.get(0);
            if (split.size() == 2) return split.get(1);
            throw new IllegalArgumentException("There should be column title or column title with alias");
        }).collect(Collectors.toList());
        return collect;
    }

    private static List<String> splitNotBlank(String queryString, String regex) {
        return Arrays.stream(queryString.split(regex)).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    public static void printTable(String title, String[][] values) {
        if (values.length == 0) {
            syncPrintln(() -> {
                System.out.println(LINE_SYMBOL.repeat(title.length()));
                System.out.println(title);
                System.out.println(LINE_SYMBOL.repeat(title.length()));
            });
        } else {
            String template = template(values[0].length);
            Optional<Integer> reduced = Arrays.stream(values[0]).map(String::length).reduce(Integer::sum);
            Integer lineLength = reduced.get() + 2 + values.length;


            syncPrintln(() -> {
                System.out.println(LINE_SYMBOL.repeat(lineLength));
                System.out.println(title);
                for (String[] value : values) {
                    PrintUtils.printfln(template, (Object[]) value);
                }
                System.out.println(LINE_SYMBOL.repeat(lineLength));
            });
        }
    }

    private static String template(int fieldsAmount) {
        return StringUtils.joinWith(DELIMITER, Collections.nCopies(fieldsAmount, "%s"));
    }

    public static Runnable throwNotFound(AutoGeneratedId<?> entity) {
        return throwNotFound(ClassUtils.simpleName(entity), entity.getId());
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

}
