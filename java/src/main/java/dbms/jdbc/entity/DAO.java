package dbms.jdbc.entity;

import dbms.jdbc.FieldGetter;
import dbms.jdbc.FieldSetter;
import dbms.jdbc.ResultGetter;
import dbms.jdbc.StatementSetter;
import dbms.jdbc.dbms.SQLStrategy;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import utils.PrintUtils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.System.out;

public abstract class DAO<Id, E extends JdbcEntity<Id>> {

    private final SQLStrategy sqlStrategy;
    private final String selectByIds;
    private final String selectById;
    private final String deleteById;
    private final String insertUser;

    public final E emptyEntity = emptyEntityCreator().get();
    private final String idName = emptyEntity.getIdName();

    @Getter
    private final String tableName;

    public abstract Supplier<E> emptyEntityCreator();

    public abstract Map<String, Pair<ResultGetter, FieldSetter<E>>> resultGettersAndEntitySetters();
    public abstract Map<String, Pair<FieldGetter<E>, StatementSetter<?>>> statementSettersAndEntityGetters();


    private final StatementSetter<Id> idSetter = (StatementSetter<Id>) statementSettersAndEntityGetters().get(idName).getRight();


    protected DAO(SQLStrategy sqlStrategy) {
        this.sqlStrategy = sqlStrategy;

        tableName = sqlStrategy.getSchema() + "." + emptyEntity.getTableName();

        selectByIds = sqlStrategy.selectByIdsQuery(tableName, idName);
        selectById = sqlStrategy.selectByIdQuery(tableName, idName);
        deleteById = sqlStrategy.deleteByIdQuery(tableName, idName);
        insertUser = sqlStrategy.insertUserQuery(tableName, emptyEntity.getTableColumns());
    }

    public void truncate(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        int inserted = stmt.executeUpdate("TRUNCATE " + tableName);
        System.out.println(inserted > 0 ? "Successfully Inserted" : "Insert Failed");
    }

    public Optional<E> selectById(Connection connection, Id id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(selectById);
        idSetter.accept(stmt, id);
        ResultSet resultSet = stmt.executeQuery();
        if(resultSet.next()) {
            return Optional.of(convert(resultSet));
        } else {
            return Optional.empty();
        }
    }

    public List<E> selectByIds(Connection connection, List<Id> ids) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(selectByIds);
        Object[] idsArray = ids.stream().toArray();
        Array array = stmt.getConnection().createArrayOf(getType(), idsArray);
        stmt.setArray(1, array);
        ResultSet resultSet = stmt.executeQuery();
        List<E> entities = new ArrayList<>();
        while (resultSet.next()) {
            E entity = convert(resultSet);
            entities.add(entity);
        }
        return entities;
    }

    public void deleteById(Connection connection, Id id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(deleteById);
        idSetter.accept(stmt, id);
        stmt.execute();
    }

    public void insert(Connection con, E entity) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(insertUser);
        statementSettersAndEntityGetters().forEach((column, statementSetterAndEntityGetter) -> {
            FieldGetter<E> entityFieldGetter = statementSetterAndEntityGetter.getLeft();
            Object entityValue = entityFieldGetter.apply(entity);
            StatementSetter statementSetter = statementSetterAndEntityGetter.getRight();
            statementSetter.accept(stmt, entityValue);
        });
        stmt.execute();
    }

    private E convert(ResultSet resultSet) {
        E entity = emptyEntityCreator().get();
        resultGettersAndEntitySetters().forEach((columns, functions) -> {
            ResultGetter resultGetter = functions.getLeft();
            Object value = resultGetter.apply(resultSet);
            FieldSetter<E> fieldSetter = functions.getRight();
            fieldSetter.accept(entity, value);
        });
        return entity;
    }

    private String getType() {
        Class<Id> idType = emptyEntity.getIdType();
        if (idType.equals(String.class)) return sqlStrategy.getVarcharTypeName();
        if (idType.equals(Integer.class)) return sqlStrategy.getIntegerTypeName();
        throw new RuntimeException("There is no dataType for ");
    }
}