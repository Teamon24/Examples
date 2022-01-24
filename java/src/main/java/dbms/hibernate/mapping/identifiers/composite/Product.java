package dbms.hibernate.mapping.identifiers.composite;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id private Integer id;
}
