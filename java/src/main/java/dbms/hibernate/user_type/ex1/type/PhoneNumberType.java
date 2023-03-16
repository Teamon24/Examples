package dbms.hibernate.user_type.ex1.type;

import dbms.hibernate.user_type.ex1.PhoneNumber;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import utils.ClassUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class PhoneNumberType implements UserType {

    public static final PhoneNumberType INSTANCE = new PhoneNumberType();

    @Override
    public int[] sqlTypes() {
        return new int[] {
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER
        };
    }

    @Override
    public Class returnedClass() {
        return PhoneNumber.class;
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
        throws HibernateException, SQLException
    {

        if (rs.wasNull()) return null;

        int countryCode = rs.getInt(names[0]);
        int cityCode = rs.getInt(names[1]);
        int number = rs.getInt(names[2]);

        return new PhoneNumber(countryCode, cityCode, number);
    }

    @Override
    public void nullSafeSet(
        PreparedStatement st, Object value,
        int index, SharedSessionContractImplementor session)
        throws HibernateException, SQLException {

        if (Objects.isNull(value)) {
            st.setNull(index, Types.INTEGER);
            st.setNull(index + 1, Types.INTEGER);
            st.setNull(index + 2, Types.INTEGER);
        } else {
            PhoneNumber phoneNumber = (PhoneNumber) value;
            st.setInt(index, phoneNumber.getCountryCode());
            st.setInt(index+1,phoneNumber.getCityCode());
            st.setInt(index+2,phoneNumber.getNumber());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return new PhoneNumber((PhoneNumber) value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        throw new UnsupportedOperationException(ClassUtils.simpleName(this) + "#assemble");
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        throw new UnsupportedOperationException(ClassUtils.simpleName(this) + "#replace");
    }
}