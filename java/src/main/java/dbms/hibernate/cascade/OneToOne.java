package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.save;
import static utils.PrintUtils.printfln;

public interface OneToOne {


    /**
     * <pre>{@code
     * create sequence hibernate_sequence start 1 increment 1
     * create table Post (
     *      id int8 not null,
     *      name varchar(255),
     *      primary key (id))
     * create table PostDetails (
     *      created_on timestamp,
     *      visible boolean not null,
     *      post_id int8 not null,
     *      primary key (post_id)
     * )
     * }
     * </pre>
     */
    static void main(String[] args) {
        bidirectional();
//        unidirectional();
    }

    private static void bidirectional() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(
            "/META-INF/hibernate-postgresql-example.cfg.xml",
            Car.class, CarDetails.class);

        Car car = new Car();
        Session session = sessionFactory.openSession();
        cascadeSave(session, car);
        cascadeMerge(session, car);
        cascadeDelete(session, car);

        Car newCar = new Car();
        newCar.addDetails(new CarDetails());
        save(session, newCar);

        cascadeOrphanRemove(session, newCar.getId());

        HibernateUtils.findAll(session, Car.class).forEach(c ->
            printfln("Car: id = '%s', car details: id = '%s', car_id = '%s'",
                c.getId(),
                c.getDetailsId(),
                c.getDetails() == null ? null : c.getDetails().getCarId())
        );
    }

    static Session cascadeSave(Session session, Car car) {
        car.setName("Hibernate Master Class");
        CarDetails details = new CarDetails();
        car.addDetails(details);

        save(session, car);
        return session;
    }

    static void cascadeMerge(Session session, Car car) {
        car.setName("Hibernate Master Class Training Material");
        car.getDetails().setVisible(true);
        commit(session, Session::merge, car);
    }

    static void cascadeDelete(Session session, Object entity) {
        commit(session, Session::delete, entity);
    }

    static void cascadeOrphanRemove(Session session, Long id) {
        commit(session, s -> {
            Car car = session.get(Car.class, id);
            car.removeDetails(car.getDetails());
        });
    }

}

