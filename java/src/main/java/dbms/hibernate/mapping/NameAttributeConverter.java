package dbms.hibernate.mapping;

import dbms.jpa.ex1.UserEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class NameAttributeConverter implements AttributeConverter<UserEntity.Name, String> {

  protected static final String SEPARATOR = " ";

  @Override
    public String convertToDatabaseColumn(UserEntity.Name attribute) {

        String fname = attribute.getFirstName() == null ? "anonymous" : attribute.getFirstName();
        String lname = attribute.getLastName() == null ? "" : attribute.getLastName();

        return fname + SEPARATOR + lname;
    }

    @Override
    public UserEntity.Name convertToEntityAttribute(String dbData) {

        if (dbData != null && dbData.split(SEPARATOR).length > 0) {
            UserEntity.Name name = new UserEntity.Name();
            name.setFirstName(dbData.split(SEPARATOR)[0]);
            name.setLastName(dbData.split(SEPARATOR)[1]);

            return name;
        }

        return null;
    }
}