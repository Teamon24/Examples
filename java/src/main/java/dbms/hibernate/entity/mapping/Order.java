package dbms.hibernate.entity.mapping;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
public class Order {
    @EmbeddedId
    private OrderPK id;
}

@Embeddable
class OrderPK implements Serializable {
    private long orderId;
    private long productId;
}

@Entity
@IdClass(OrderPK.class)
class OrderPK_2 {

    @Id
    private long orderId;
    @Id
    private long productId;
}