package dbms.hibernate.relashonship.one_to_many;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.operate;
import static dbms.hibernate.TransactionUtils.refresh;
import static utils.PrintUtils.printfln;

/**
 * Unidirectional is a relation where one side does not know about the relation.
 */
public interface Unidirectional {
    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql-example.cfg.xml";
        SessionFactory sessionFactory = new SessionFactoryBuilder()
            .resourceName(resourceName)
            .entitiesClasses(CartUni.class, ItemUni.class)
            .build();

        Session session = sessionFactory.openSession();

        CartUni cartUni = new CartUni();
        ItemUni itemUni = new ItemUni();
        ItemUni itemUni2 = new ItemUni();
        cartUni.addItems(itemUni, itemUni2);
        List<Object> entities = operate(session, Session::save, cartUni, itemUni, itemUni2);
        commit(session, s -> {});
        refresh(session, entities);

        HibernateUtils.findAll(session, CartUni.class).forEach(cart ->
            printfln("Cart id = %s, items ids = %s",
                cart.getId(),
                cart.getItemsIds()));
    }
}
