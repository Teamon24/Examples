package jpa.jdbc.entity;

import jpa.jdbc.dbms.SQLStrategy;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class DAO<Id, T extends Entity<Id>> {

    private final SQLStrategy sqlStrategy;
    private final String selectByIds;
    private final String selectById;
    private final String deleteById;
    private final String insertUser;

    public final T emptyEntity = emptyEntityCreator().get();
    private final String idName = emptyEntity.getIdName();

    @Getter
    private final String tableName;

    public abstract
    Map<
        String,
        Pair<
            Function<ResultSet, Object>,
            BiConsumer<T, Object>
            >
        >
    resultGettersAndEntitySetters();

    public abstract
    Map<
        String,
        Pair<
            BiConsumer<PreparedStatement, Object>,
            Function<T, Object>
            >
        >
    statementSettersAndEntityGetters();


    private final BiConsumer<PreparedStatement, Id> idSetter = emptyEntity.idResultSetter();


    protected DAO(SQLStrategy sqlStrategy) {
        this.sqlStrategy = sqlStrategy;

        tableName = sqlStrategy.getSchema() + "." + emptyEntity.getTableName();

        selectByIds = sqlStrategy.selectByIdsQuery(tableName, idName);
        selectById = sqlStrategy.selectByIdQuery(tableName, idName);
        deleteById = sqlStrategy.deleteByIdQuery(tableName, idName);
        insertUser = sqlStrategy.insertUserQuery(tableName, emptyEntity.getTableColumns());
    }

    public abstract Supplier<T> emptyEntityCreator();

    public void truncate(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        int inserted = stmt.executeUpdate("TRUNCATE " + tableName);
        System.out.println(inserted > 0 ? "Successfully Inserted" : "Insert Failed");
    }

    public T selectById(Connection connection, Id id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(selectById);
        idSetter.accept(stmt, id);
        ResultSet resultSet = stmt.executeQuery();
        if(resultSet.next()) {
            return convert(resultSet);
        } else {
            return null;
        }
    }

    public List<T> selectByIds(Connection connection, List<Id> ids) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(selectByIds);
        Object[] idsArray = ids.stream().toArray();
        Array array = stmt.getConnection().createArrayOf(getType(), idsArray);
        stmt.setArray(1, array);
        ResultSet resultSet = stmt.executeQuery();
        List<T> entities = new ArrayList<>();
        while (resultSet.next()) {
            T entity = convert(resultSet);
            entities.add(entity);
        }
        return entities;
    }

    public void deleteById(Connection connection, Id id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(deleteById);
        idSetter.accept(stmt, id);
        stmt.execute();
    }

    public void insert(Connection con, T entity) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertUser);
        statementSettersAndEntityGetters().forEach((column, statementSetterAndEntityGetter) -> {
            Object entityValue = getFieldValue(statementSetterAndEntityGetter, entity);
            setStatementValue(statementSetterAndEntityGetter, stmt, entityValue);
        });
        stmt.execute();
    }

    private T convert(ResultSet resultSet) {
        T entity = emptyEntityCreator().get();
        resultGettersAndEntitySetters().forEach((columns, functions) -> {
            Object value = getResultSetValue(functions, resultSet);
            setField(functions, entity, value);
        });
        return entity;
    }

    private String getType() {
        Class<Id> idType = emptyEntity.getIdType();
        if (idType.equals(String.class)) return sqlStrategy.getVarcharTypeName();
        if (idType.equals(Integer.class)) return sqlStrategy.getIntegerTypeName();
        throw new RuntimeException("There is no dataType for ");
    }

    private void setField(
        Pair<Function<ResultSet, Object>,
        BiConsumer<T, Object>> functions, T entity,
        Object value)
    {
        BiConsumer<T, Object> entityFieldSetter = functions.getRight();
        entityFieldSetter.accept(entity, value);
    }

    private Object getResultSetValue(
        Pair<Function<ResultSet, Object>,
        BiConsumer<T, Object>> functions,
        ResultSet resultSet)
    {
        Function<ResultSet, Object> columnValueGetter = functions.getLeft();
        Object value = columnValueGetter.apply(resultSet);
        return value;
    }


    private void setStatementValue(
        Pair<BiConsumer<PreparedStatement,
        Object>, Function<T, Object>> statementSetterAndEntityGetter,
        PreparedStatement stmt,
        Object entityValue)
    {
        BiConsumer<PreparedStatement, Object> statementSetter = statementSetterAndEntityGetter.getLeft();
        statementSetter.accept(stmt, entityValue);
    }

    private Object getFieldValue(
        Pair<BiConsumer<PreparedStatement, Object>, Function<T, Object>> statementSetterAndEntityGetter,
        T entity)
    {
        Function<T, Object> entityFieldGetter = statementSetterAndEntityGetter.getRight();
        Object entityValue = entityFieldGetter.apply(entity);
        return entityValue;
    }

}