package dbms.hibernate.user_type.ex1.type;

import dbms.hibernate.user_type.ex1.ExampleUtils;
import dbms.hibernate.user_type.ex1.Salary;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;
import utils.ClassUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

public class SalaryType implements CompositeUserType, DynamicParameterizedType {

    public static final SalaryType INSTANCE = new SalaryType();

    public static final String CURRENCY = "currency";

    private String localCurrency;

    @Override
    public void setParameterValues(Properties parameters) {
        this.localCurrency = parameters.getProperty(CURRENCY);
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"amount", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[] {
            IntegerType.INSTANCE,
            StringType.INSTANCE
        };
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        Salary salary = (Salary) component;

        switch (property) {
            case 0:
                return salary.getMoneyAmount();
            case 1:
                return salary.getCurrency();
            default:
                ExampleUtils.throwIllegalPropertyNumber(this, component, property, "getPropertyValue");
        }

        return null;
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        Salary salary = (Salary) component;

        switch (property) {
            case 0:
                salary.setMoneyAmount((Integer) value);
            case 1:
                salary.setCurrency((String) value);
        }

        if (property >= getPropertyNames().length) {
            ExampleUtils.throwIllegalPropertyNumber(this, component, property, "setPropertyValue");
        }
    }

    @Override
    public Class returnedClass() {
        return Salary.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(
        ResultSet rs,
        String[] names,
        SharedSessionContractImplementor session,
        Object owner
    )
        throws HibernateException, SQLException {
        if (rs.wasNull()) return null;

        Integer amount = rs.getInt(names[0]);
        String currency = rs.getString(names[1]);

        return new Salary(amount, currency);
    }

    @Override
    public void nullSafeSet(
        PreparedStatement st,
        Object value,
        int index,
        SharedSessionContractImplementor session
    )
        throws HibernateException, SQLException {
        if (Objects.isNull(value)) {
            st.setNull(index, Types.INTEGER);
            st.setNull(index + 1, Types.VARCHAR);
        } else {
            Salary salary = (Salary) value;
            st.setInt(index, salary.getMoneyAmount());
            st.setString(index + 1, salary.getCurrency());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return new Salary((Salary) value);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
        throw new UnsupportedOperationException(ClassUtils.simpleName(this) + "#disassemble");

    }

    @Override
    public Object assemble(
        Serializable cached,
        SharedSessionContractImplementor session,
        Object owner
    ) throws HibernateException {
        throw new UnsupportedOperationException(ClassUtils.simpleName(this) + "#assemble");
    }

    @Override
    public Object replace(
        Object original,
        Object target,
        SharedSessionContractImplementor session,
        Object owner
    ) throws HibernateException {
        throw new UnsupportedOperationException(ClassUtils.simpleName(this) + "#replace");
    }
}