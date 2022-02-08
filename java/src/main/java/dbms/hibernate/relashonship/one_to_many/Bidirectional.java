package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.persist;
import static dbms.hibernate.TransactionUtils.refresh;
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
 * <li>The <strong>inverse side</strong> of a bidirectional relationship must refer to its <strong>owning side</strong>(Entity which contains the <strong>foreign key</strong>) by using the <strong><i>mappedBy</i></strong> element of the @OneToOne, @OneToMany, or @ManyToMany annotation. The mappedBy element designates the property or field in the entity that is the owner of the relationship.
 * <li>The many side of @ManyToOne bidirectional relationships must not define the mappedBy element. <strong>The many side is always the owning side</strong> of the relationship.
 * <li>For @OneToOne bidirectional relationships, the owning side corresponds to the side that contains @JoinColumn i.e the corresponding foreign key.
 * <li>For @ManyToMany bidirectional relationships, either side may be the owning side.
 * <li>The bidirectional associations should <strong>always be updated on both sides</strong>, therefore the <strong>Parent</strong> side should contain the <strong>addChild</strong> and <strong>removeChild</strong> combo. These methods ensure we always synchronize both sides of the association, to avoid object or relational data corruption issues.</li>
 * </ul>
 */
public interface Bidirectional {
}

/**
 * Bidirectional relashionship, bringing in the possibility of inconsistency.
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
 */
class OwningAndInverseSavingDemo {

    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql-example.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(resourceName,
            Item.class, Cart.class, ItemAsOwner.class, CartAsOwner.class
        );

        Session session = sessionFactory.openSession();

        int amount = 5;
        saveAndRefresh(amount, session, ItemAsOwner::new, Cart::new, ItemAsOwner.class);
        saveAndRefresh(amount, session, Item::new, CartAsOwner::new, CartAsOwner.class);

        commit(session, s -> {
            HibernateUtils.findAll(s, Cart.class).forEach(cart ->
                printfln("Card id = %s, items ids = %s",
                    cart.getId(),
                    StringUtils.joinWith(", ", cart.getItemsIds())
                ));
        });

    }

    /**
     * Метод создает объекты типа I и типа С. Объектам типа I сетит по одному объекту типа С. Затем первому
     * объекту типа С сетит все объекты типа I. В зависимости от того какой тип передан
     * для сохранения (toPersistClass), происходит сохранение, и от того кто является owner'ом зависит,
     * какой будет результат сохранения.
     * @param session объект сессии.
     * @param createItem логика создания объекта типа I.
     * @param createCart логика создания объекта типа C.
     * @param toPersistClass тип объектов, которые необходимо сохранить.
     * @param <I> подтип типа ItemAbstract.
     * @param <C> подтип типа CartAbstract.
     */
    static <I extends ItemAbstract, C extends CartAbstract<I>> void saveAndRefresh(
        int amount,
        Session session,
        Function<C, I> createItem,
        Supplier<C> createCart, Class<?> toPersistClass
    ) {
        List<C> carts = repeat(amount, (i) -> createCart.get());
        List<I> items = repeat(amount, (i) -> createItem.apply(carts.get(i)));

        C firstCart = carts.get(0);
        firstCart.addItems(new HashSet<>(items));

        List<?> entities = Stream.of(carts, items).flatMap(Collection::stream).collect(Collectors.toList());

        List<?> toPersist = entities.stream()
            .filter(it -> it.getClass().equals(toPersistClass))
            .collect(Collectors.toList());

        persist(session, toPersist);
        refresh(session, entities);
    }

    static <E> List<E> repeat(int amount, Function<Integer, E> elementsSupplier) {
        List<E> elements = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            elements.add(elementsSupplier.apply(i));
        }
        return elements;
    }
}