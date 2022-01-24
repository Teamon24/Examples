package dbms.hibernate.mapping.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * JPA 2.1 release introduced a new standardized API that can be used to convert an entity attribute to a database value and vice versa.
 */
@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
 
    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public Category convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Category.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}