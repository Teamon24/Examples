package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Basic;
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
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * | carts |   | items   |
 * |-------|   |---------|
 * | id    |<--| card_id |
 * </pre>
 *
 * <p>Bidirectional relashionship, bringing in the possibility of inconsistency.
 *
 * <p>Let's imagine a situation where a developer wants to add item1 to cart and item2 to cart2, but makes a mistake so that the references between cart2 and item2 become inconsistent:
 * <pre>{@code
 * Cart cart1 = new Cart();
 * Cart cart2 = new Cart();
 *
 * Items item1 = new Items(cart1);
 * Items item2 = new Items(cart2);
 * Set<Items> itemsSet = new HashSet<Items>();
 * itemsSet.add(item1);
 * itemsSet.add(item2);
 * cart1.setItems(itemsSet); // wrong!
 * }</pre>
 *
 * <p>As shown above, item2 references cart2, whereas cart2 doesn't reference item2, and that's bad.
 * <p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Items as the Owning Side</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 *
 * <p>As stated in the JPA specification under section 2.9, it's a good practice to mark many-to-one side as the owning side.
 * <p>In other words, Items would be the owning side and Cart the inverse side, which is exactly what we did earlier.
 * <p>So how did we achieve this?
 * <p>By including the <strong>mappedBy</strong> attribute in the <i><strong>Cart</strong></i> class, we mark it as the inverse side.
 * <p>At the same time, we also annotate the Items.cart field with @ManyToOne, making Items the owning side.
 * <p>Going back to our “inconsistency” example, now Hibernate knows that the item2‘s reference is more important and will save item2‘s reference to the database.
 * <p>Let's check the result:
 * <p>
 * item1 ID=1, Foreign Key Cart ID=1
 * item2 ID=2, Foreign Key Cart ID=2
 * Although cart references item2 in our snippet, item2‘s reference to cart2 is saved in the database.
 */
public interface Bidirectional {
    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(
            resourceName,
            Cart.class, ItemAsOwner.class,
            CartAsOwner.class, Item.class
        );

        itemAsOwner(sessionFactory.getCurrentSession());
        cartAsOwner(sessionFactory.getCurrentSession());
    }

    static void itemAsOwner(Session session) {

        Cart cart1 = new Cart();
        Cart cart2 = new Cart();

        ItemAsOwner item1 = new ItemAsOwner(cart1);
        ItemAsOwner item2 = new ItemAsOwner(cart2);
        Set<ItemAsOwner> itemsSet = new HashSet<>();
        itemsSet.add(item1);
        itemsSet.add(item2);
        cart1.setItems(itemsSet); // wrong!

        Transaction transaction = session.beginTransaction();

        session.save(cart1);
        session.save(cart2);
        session.save(item1);
        session.save(item2);

        transaction.commit();

        printCart(cart1);
        printCart(cart2);
        printItem(item1);
        printItem(item2);
    }

    static void cartAsOwner(Session session) {
        CartAsOwner cart1 = new CartAsOwner();
        CartAsOwner cart2 = new CartAsOwner();

        Item item1 = new Item(cart1);
        Item item2 = new Item(cart2);
        Set<Item> itemsSet = new HashSet<>();

        itemsSet.add(item1);
        itemsSet.add(item2);
        cart1.setItems(itemsSet);

        Transaction transaction = session.beginTransaction();

        session.save(cart1);
        session.save(cart2);
        session.save(item1);
        session.save(item2);

        transaction.commit();

        printCart(cart1);
        printCart(cart2);
        printItem(item1);
        printItem(item2);
    }

    private static void printCart(CartAbstract cart) {
        System.out.println("Cart ID=" + cart.getId());
    }

    private static void printItem(ItemAbstract item) {
        System.out.println("item ID=" + item.getId() + ", Foreign Key Cart ID=" + item.getCart().getId());
    }

}

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
class Cart extends CartAbstract {
    @Setter
    @OneToMany(mappedBy = "cart")
    private Set<ItemAsOwner> items;
}

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor
class ItemAsOwner extends ItemAbstract {

    public ItemAsOwner(Cart cart) {
        this.cart = cart;
    }

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
class CartAsOwner extends CartAbstract {

    @Setter
    @OneToMany
    @JoinColumn(name = "cart_id")
    private Set<Item> items;

}

@Entity
@Table(name = "items")
@NoArgsConstructor
class Item extends ItemAbstract {

    public Item(CartAsOwner cart) {
        this.cart = cart;
    }

    @Getter
    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private CartAsOwner cart;
}


@MappedSuperclass
abstract class ItemAbstract {
    @Id @Getter
    @Column(updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="entity_item_id_sequence")
    @SequenceGenerator(name="entity_item_id_sequence", sequenceName="items_ids_sequence", allocationSize = 1)
    private Integer id;

    abstract CartAbstract getCart();
}

@MappedSuperclass
abstract class CartAbstract {
    @Getter
    @Id @Column(updatable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="entity_cart_id_sequence")
    @SequenceGenerator(name="entity_cart_id_sequence", sequenceName="carts_ids_sequence", allocationSize = 1)
    private Integer id;
}


