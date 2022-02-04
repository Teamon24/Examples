package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.TransactionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static utils.PrintUtils.printfln;

/**
 * Unidirectional is a relation where one side does not know about the relation.
 */
public interface Unidirectional {
    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql-example.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(resourceName,
            CartUni.class, ItemUni.class
        );

        Session session = sessionFactory.openSession();

        List<Object> entities = TransactionUtils.commit(session, s -> {
            CartUni cartUni = new CartUni();
            ItemUni itemUni = new ItemUni();
            ItemUni itemUni2 = new ItemUni();
            cartUni.addItems(itemUni, itemUni2);

            return TransactionUtils.transact(s, Session::save, cartUni, itemUni, itemUni2);
        });

        TransactionUtils.refresh(session, entities);

        HibernateUtils.findAll(session, CartUni.class).forEach(cart ->
            printfln("Cart id = %s, items ids = %s",
                cart.getId(),
                cart.getItemsIds()));
    }
}
