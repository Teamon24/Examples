package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import dbms.hibernate.TransactionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.function.Function;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.save;

public interface OneToOne {


    String LEFT_JOIN =
        "select new dbms.hibernate.cascade.LeftJoinCarAndCarDetails(c.name, c.id, cd.car.id, cd.visible) " +
        "from Car c " +
        "left join c.details cd";

    static void main(String[] args) {
        bidirectional();
//        unidirectional();
    }

    private static void bidirectional() {
        SessionFactory sessionFactory = new SessionFactoryBuilder()
            .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
            .entitiesClasses(Car.class, CarDetails.class)
            .build();

        Car car = new Car("Car#2 name");

        Session session = sessionFactory.openSession();
        cascadeSave(session, car);
        cascadeMerge(session, car);
        cascadeDelete(session, car);

        Car newCar = new Car("Car#1 name").addDetails(new CarDetails());
        TransactionUtils.save(session, newCar);

        cascadeOrphanRemove(session, newCar.getId());
    }

    static Session cascadeSave(Session session, Car car) {
        TransactionUtils.save(session,
            car.setName("Hibernate Master Class")
                .addDetails(new CarDetails()));
        printJoin(session, "cascade save");
        return session;
    }

    static void cascadeMerge(Session session, Car car) {
        car
            .setName("merged car name")
            .getDetails()
            .setVisible(true);

        TransactionUtils.commit(Session::merge, session, car);
        printJoin(session, "cascade merge");
    }

    static void cascadeDelete(Session session, Object entity) {
        TransactionUtils.commit(Session::delete, session, entity);
        printJoin(session, "cascade delete");
    }

    static void cascadeOrphanRemove(Session session, Integer id) {
        TransactionUtils.commit(session, s -> {
            Car car = session.get(Car.class, id);
            car.removeDetails(car.getDetails());
        });
        printJoin(session, "cascade orphan remove");
    }

    private static void printJoin(Session session, String title) {
        HibernateUtils.printJoin(session,
            title,
            LEFT_JOIN,
            LeftJoinCarAndCarDetails::getCarId,
            LeftJoinCarAndCarDetails.getters());
    }
}

@Getter
@AllArgsConstructor
class LeftJoinCarAndCarDetails {
    private String carName;
    private Integer carId;
    private Integer carDetailsCarId;
    private Boolean carDetailsVisible;

    public static List<Function<LeftJoinCarAndCarDetails, ?>> getters() {
        return List.of(
            LeftJoinCarAndCarDetails::getCarName,
            LeftJoinCarAndCarDetails::getCarId,
            LeftJoinCarAndCarDetails::getCarDetailsCarId,
            LeftJoinCarAndCarDetails::getCarDetailsVisible
        );
    }
}

