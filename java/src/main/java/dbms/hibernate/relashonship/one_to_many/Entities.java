package dbms.hibernate.relashonship.one_to_many;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Entities {}

@Getter
@MappedSuperclass
abstract class ItemAbstract {
    @Id
    @Column(name = "id", updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="item_id_sequence_generator")
    @SequenceGenerator(
        name="item_id_sequence_generator",
        sequenceName="ITEMS_IDS_SEQUENCE",
        allocationSize = 1
    )
    private Integer id;
}

class a extends SequenceStyleGenerator {

}

@Getter
@MappedSuperclass
abstract class CartAbstract<T extends ItemAbstract> {

    @Id
    @Column(name = "id", updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cart_id_sequence_generator")
    @SequenceGenerator(
        name="cart_id_sequence_generator",
        sequenceName="CARTS_IDS_SEQUENCE",
        allocationSize = 1
    )
    private Integer id;

    protected abstract Collection<T> getItems();

    public List<Integer> getItemsIds() {
        return this.getItems().stream().map(ItemAbstract::getId).collect(Collectors.toList());
    }

    public void addItems(Collection<T> items) {
        this.getItems().addAll(items);
    }

    @SafeVarargs
    public final CartAbstract<T> addItems(T... items) {
        this.getItems().addAll(Arrays.asList(items));
        return this;
    }
}

/*----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------*/
@Entity
@Table(name="carts")
class CartUni extends CartAbstract<ItemUni> {

    @Getter
    @OneToMany
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Set<ItemUni> items = new HashSet<>();
}

@Entity
@Table(name="items")
@NoArgsConstructor
class ItemUni extends ItemAbstract {

    @Column(name="cart_id", insertable = false, updatable = false)
    private long cartId;
}

/*----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------*/
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
class Cart extends CartAbstract<ItemAsOwner> {

    @Getter
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<ItemAsOwner> items = new HashSet<>();
}

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
class ItemAsOwner extends ItemAbstract {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}

/*----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------*/
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
class CartAsOwner extends CartAbstract<Item> {

    @Getter
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Set<Item> items = new HashSet<>();
}

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
class Item extends ItemAbstract {

    @Getter
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private CartAsOwner cart;
}







