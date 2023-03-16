package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Common 'id' part of all entities.
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractIdentifiableObject {
    /**
     * Common id field.
     */
    private Integer id;
}