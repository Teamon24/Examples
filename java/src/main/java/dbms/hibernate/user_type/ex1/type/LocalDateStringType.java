package dbms.hibernate.user_type.ex1.type;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.LocalDateType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import java.time.LocalDate;

public class LocalDateStringType
  extends AbstractSingleColumnStandardBasicType<LocalDate> {

    public static final LocalDateStringType INSTANCE = new LocalDateStringType();

    public LocalDateStringType() {
        super(VarcharTypeDescriptor.INSTANCE, LocalDateStringJavaDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "LocalDateString";
    }

}

class LocalDateStringJavaDescriptor extends AbstractTypeDescriptor<LocalDate> {

    public static final LocalDateStringJavaDescriptor INSTANCE = new LocalDateStringJavaDescriptor();

    public LocalDateStringJavaDescriptor() {
        super(LocalDate.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public LocalDate fromString(String string) {
        return LocalDate.from(LocalDateType.FORMATTER.parse(string));
    }


    @Override
    public <X> X unwrap(LocalDate value, Class<X> type, WrapperOptions options) {
        if (value == null) return null;
        if (String.class.isAssignableFrom(type)) return (X) LocalDateType.FORMATTER.format(value);
        throw unknownUnwrap(type);
    }

    @Override
    public <X> LocalDate wrap(X value, WrapperOptions options) {
        if (value == null) return null;
        if(value instanceof String) return LocalDate.from(LocalDateType.FORMATTER.parse((CharSequence) value));
        throw unknownWrap(value.getClass());
    }
}