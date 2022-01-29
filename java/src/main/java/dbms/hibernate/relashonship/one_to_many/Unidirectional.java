package dbms.hibernate.relashonship.one_to_many;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

public interface Unidirectional {
}


@Entity
@Table(name="carts")
class Cart1 {
    @OneToMany(mappedBy="cart")
    private Set<Item1> items;
}

@Entity
@Table(name="items")
class Item1 {
    private Integer id;
}
