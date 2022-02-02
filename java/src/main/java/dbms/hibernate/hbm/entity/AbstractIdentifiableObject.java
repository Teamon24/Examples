package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Common 'id' part of all entities.
 */
@ToString
@Getter
@Setter
public abstract class AbstractIdentifiableObject {
    /**
     * Common id field.
     */
    private Integer id;
}