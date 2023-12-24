package dbms.hibernate.relashonship.one_to_many;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

public interface Entities {}

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







