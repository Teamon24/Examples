package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.TransactionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static dbms.hibernate.TransactionUtils.commit;
import static utils.PrintUtils.printfln;

/**
 * <pre>
 * | carts |   | items   |
 * |-------|   |---------|
 * | id    |<--| cart_id |
 * </pre>
 *
 * <p>As a general rule the owning side of a relation would the side which you'd need to update for the change of the relation to be persisted.
 * <p> Bidirectional relationships must follow these rules.
 * <ul>
 * <li>The <strong>inverse side</strong> of a bidirectional relationship must refer to its <strong>owning side</strong>(Entity which contains the foreign key) by using the <strong><i>mappedBy</i></strong> element of the @OneToOne, @OneToMany, or @ManyToMany annotation. The mappedBy element designates the property or field in the entity that is the owner of the relationship.
 * <li>The many side of @ManyToOne bidirectional relationships must not define the mappedBy element. The many side is always the owning side of the relationship.
 * <li>For @OneToOne bidirectional relationships, the owning side corresponds to the side that contains @JoinColumn i.e the corresponding foreign key.
 * <li>For @ManyToMany bidirectional relationships, either side may be the owning side.
 * </ul>
 * <p>Bidirectional relashionship, bringing in the possibility of inconsistency.
 * <p>Let's imagine a situation where a developer wants to add item1 to cart and item2 to cart2, but makes a mistake so that the references between cart2 and item2 become inconsistent:
 * <pre>{@code
 * Cart cart1 = new Cart();
 * Cart cart2 = new Cart();
 * Items item1 = new Items(cart1);
 * Items item2 = new Items(cart2);
 * Set<Items> itemsSet = new HashSet<Items>();
 * itemsSet.add(item1);
 * itemsSet.add(item2);
 * cart1.setItems(itemsSet);
 * }</pre>
 * <p>As shown above, item2 references cart2, whereas cart2 doesn't reference item2, and that's bad.
 * <p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Items as the Owning Side</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>As stated in the JPA specification under section 2.9, it's a good practice to mark many-to-one side as the owning side.
 * <pre>{@code
 * public class Cart {
 *     @OneToMany(mappedBy="cart")
 *     private Set<Items> items;
 * }
 * public class Items {
 *     @ManyToOne
 *     @JoinColumn(name="cart_id", nullable=false)
 *     private Cart cart;
 * }
 * }</pre>
 * <p>By including the <strong>mappedBy</strong> attribute in the <i><strong>Cart</strong></i> class, we mark it as the inverse side.
 * <p>At the same time, we also annotate the Items.cart field with @ManyToOne, making Items the owning side.
 * <p>Going back to our “inconsistency” example, now Hibernate knows that the item2‘s reference is more important and will save item2‘s reference to the database.
 * <p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Carts as the Owning Side</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>It's also possible to mark the one-to-many side as the owning side, and many-to-one side as the inverse side.
 * <p>Although this is not a recommended practice, let's go ahead and give it a try.
 * <p>The code snippet below shows the implementation of one-to-many side as the owning side:
 * <pre>{@code
 * public class Item {
 *     @ManyToOne
 *     @JoinColumn(name = "cart_id", insertable = false, updatable = false)
 *     private CartOIO cart;
 * }
 * public class Cart {
 *     @OneToMany
 *     @JoinColumn(name = "cart_id") // we need to duplicate the physical information
 *     private Set<ItemO> items;
 * }
 * }</pre>
 * <p>Notice how we removed the mappedBy element and set the many-to-one @JoinColumn as insertable and updatable to false.
 * <p>If we run the same code, the result will be the opposite:
 * <pre>{@code
 * item1 ID=1, Foreign Key Cart ID=1
 * item2 ID=2, Foreign Key Cart ID=1
 * }</pre>
 * <p>As shown above, now item2 belongs to cart.
 */
public interface Bidirectional {

    int AMOUNT = 5;

    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(resourceName,
            Item.class, Cart.class, ItemAsOwner.class, CartAsOwner.class
        );

        Session session = sessionFactory.openSession();

        test(session, ItemAsOwner::new, Cart::new, doNotAddItems(), ItemAsOwner.class);
        test(session, Item::new, CartAsOwner::new, CartAbstract::addItems, CartAsOwner.class);

        // Переоткрытие сессии необходимо для обновления Persistence context,
        // иначе результат вывода на экран будет другой
        session.close();
        session = sessionFactory.openSession();

        TransactionUtils.commit(session, s -> {
            HibernateUtils.findLast(s, Cart.class, AMOUNT * 2).forEach(cart ->
                printfln("Card id = %s, items ids = %s",
                    cart.getId(),
                    StringUtils.joinWith(", ", cart.getItemsIds())
                ));
        });

    }

    static <I extends ItemAbstract, C extends CartAbstract<I>> void test(
        Session session,
        Function<C, I> createItem,
        Supplier<C> createCart,
        BiConsumer<C, Set<I>> addItems,
        Class<?> toPersistClass
    ) {
        List<C> carts = repeat(AMOUNT, createCart);
        List<I> items = repeat(AMOUNT, (i) -> createItem.apply(carts.get(i)));

        addItems.accept(carts.get(0), new HashSet<>(items));

        commit(session, (s) -> {
            Stream.of(carts, items)
                .flatMap(Collection::stream)
                .filter(it -> it.getClass().equals(toPersistClass))
                .forEach(s::persist);
        });
    }

    static <E> List<E> repeat(int amount, Supplier<E> elementsSupplier) {
        List<E> elements = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            elements.add(elementsSupplier.get());
        }
        return elements;
    }

    static <E> List<E> repeat(int amount, Function<Integer, E> elementsSupplier) {
        List<E> elements = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            elements.add(elementsSupplier.apply(i));
        }
        return elements;
    }

    private static BiConsumer<Cart, Set<ItemAsOwner>> doNotAddItems() {
        return (c, is) -> {};
    }
}