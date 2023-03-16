package dbms.hibernate.user_type.ex1.type;

import dbms.hibernate.user_type.ex1.Address;
import dbms.hibernate.user_type.ex1.ExampleUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import utils.ClassUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Objects;

public class AddressType implements CompositeUserType {

    public static final AddressType INSTANCE = new AddressType();

    public static final LinkedHashMap<String, ? extends Type> properties = new LinkedHashMap<>() {{
        put("addressLine1",  StringType.INSTANCE);
        put("addressLine2",  StringType.INSTANCE);
        put("country",       StringType.INSTANCE);
        put("city",          StringType.INSTANCE);
        put("zipcode",       StringType.INSTANCE);
    }};

    @Override
    public String[] getPropertyNames() {
        return properties.keySet().toArray(new String[0]);
    }

    @Override
    public Type[] getPropertyTypes() {
        return properties.values().toArray(new Type[0]);
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {

        Address empAdd = (Address) component;

        switch (property) {
            case 0: return empAdd.getAddressLine1();
            case 1: return empAdd.getAddressLine2();
            case 2: return empAdd.getCity();
            case 3: return empAdd.getCountry();
            case 4: return empAdd.getZipCode();
            default: ExampleUtils.throwIllegalPropertyNumber(this, component, property, "getPropertyValue");
        }

        return null;
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        Address empAdd = (Address) component;

        switch (property) {
            case 0: empAdd.setAddressLine1((String) value);
            case 1: empAdd.setAddressLine2((String) value);
            case 2: empAdd.setCity((String) value);
            case 3: empAdd.setCountry((String) value);
            case 4: empAdd.setZipCode((String) value);
        }

        if (property >= getPropertyNames().length) {
            ExampleUtils.throwIllegalPropertyNumber(this, component, property, "setPropertyValue");
        }
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
        throws HibernateException, SQLException
    {
        if (rs.wasNull()) return null;

        String addressLine1 = rs.getString(names[0]);
        String addressLine2 = rs.getString(names[1]);
        String city = rs.getString(names[2]);
        String country = rs.getString(names[3]);
        String zipCode = rs.getString(names[4]);

        return new Address(addressLine1, addressLine2, country, city, zipCode);
    }

    @Override
    public void nullSafeSet(
        PreparedStatement st, Object value, int index, SharedSessionContractImplementor session
    ) throws HibernateException, SQLException {
        if (Objects.isNull(value)) {
            st.setNull(index,     Types.VARCHAR);
            st.setNull(index + 1, Types.VARCHAR);
            st.setNull(index + 2, Types.VARCHAR);
            st.setNull(index + 3, Types.VARCHAR);
            st.setNull(index + 4, Types.VARCHAR);
        } else {
            Address address = (Address) value;
            st.setString(index,     address.getAddressLine1());
            st.setString(index + 1, address.getAddressLine2());
            st.setString(index + 2, address.getCountry());
            st.setString(index + 3, address.getCity());
            st.setString(index + 4, address.getZipCode());
        }
    }

    @Override
    public Class returnedClass() {
        return Address.class;
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
    public Object deepCopy(Object value) throws HibernateException {
        return new Address((Address) value);
    }

    @Override
    public boolean isMutable() {
        return true;
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