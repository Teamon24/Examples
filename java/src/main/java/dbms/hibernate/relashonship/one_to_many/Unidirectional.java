package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.TransactionUtils;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static utils.PrintUtils.printfln;

/**
 * Unidirectional is a relation where one side does not know about the relation.
 */
public interface Unidirectional {
    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(resourceName,
            CartUni.class, ItemUni.class
        );

        Session session = sessionFactory.openSession();

        CartUni cartUni = new CartUni();

        ItemUni itemUni = new ItemUni();
        ItemUni itemUni2 = new ItemUni();

        cartUni.addItems(itemUni, itemUni2);

        TransactionUtils.commit(session, s -> {
            s.save(cartUni);
            s.save(itemUni);
            s.save(itemUni2);
        });

        session.close();
        session = sessionFactory.openSession();

        HibernateUtils.findLast(session, CartUni.class, 1).forEach(cart ->
            printfln("Cart id = %s, items ids = %s",
                cart.getId(),
                cart.getItemsIds()));
    }
}

@Entity
@Table(name="carts")
class CartUni extends CartAbstract<ItemUni> {

    @Getter
    @OneToMany
    @JoinColumn(name = "cart_id")
    private Set<ItemUni> items = new HashSet<>();
}

@Entity
@Table(name="items")
class ItemUni extends ItemAbstract {
}

