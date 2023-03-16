
package dbms.hibernate.identifiers.composite;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


/**
 * <p>Both types of composite ids (@IdClass, @Embeddable), the primary key class can also contain @ManyToOne attributes.
 * <p>Hibernate also allows defining primary keys made up of @ManyToOne associations combined with @Id annotation.
 * In this case, the entity class should also fulfill the conditions of a primary key class.
 * <p>However, the disadvantage of this method is that there's no separation between the entity object and the identifier.
 *
 * <p>------------------------------------------------------------------------------------------------------------------</p>
 * <p></p>
 * The primary key class must fulfill several conditions:
 * <ul>
 * <li>It should be defined using @EmbeddedId or @IdClass annotations.</li>
 * <li>It should be public, serializable and have a public no-arg constructor.</li>
 * <li>Finally, it should implement equals() and hashCode() methods.</li>
 * </ul>
 * The class's attributes can be basic, composite or ManyToOne, while avoiding collections and OneToOne attributes.
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
class OrderProductCompositePrimaryKey implements Serializable {
    private long orderId;
    private long productId;

    @Override public boolean equals(Object o) { throw new UnsupportedOperationException(); }
    @Override public int hashCode() { throw new UnsupportedOperationException(); }

    @Getter
    @Setter
    @ManyToOne
    private Order order;

    @Getter
    @Setter
    @ManyToOne
    private Product product;
}

@Entity
public class OrderProduct {
    @EmbeddedId private OrderProductCompositePrimaryKey id;

    @ManyToOne
    @JoinColumn(name = "order", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Product product;

}

/**
 * The @IdClass annotation is similar to the @EmbeddedId. The difference with @IdClass is that the attributes are defined in the main entity class using @Id for each one. The primary key class will look the same as before.
 */
@Entity
@IdClass(OrderProductCompositePrimaryKey.class)
class OrderProduct2 {
    @Id private long orderId;
    @Id private long productId;
}