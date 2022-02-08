package dbms.hibernate.cascade;

import dbms.hibernate.HibernateUtils;
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
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(
            "/META-INF/hibernate-postgresql-example.cfg.xml",
            Car.class, CarDetails.class);

        Car car = new Car("Car#2 name");

        Session session = sessionFactory.openSession();
        cascadeSave(session, car);
        printJoin(session, "cascade save");

        cascadeMerge(session, car);
        printJoin(session, "cascade merge");

        cascadeDelete(session, car);
        printJoin(session, "cascade delete");

        Car newCar = new Car("Car#1 name");
        newCar.addDetails(new CarDetails());
        save(session, newCar);
        printJoin(session, "save new car");

        cascadeOrphanRemove(session, newCar.getId());
        printJoin(session, "cascade orphans remove");

    }

    private static void printJoin(Session session, String title) {
        HibernateUtils.printJoin(session,
            title,
            LEFT_JOIN,
            LeftJoinCarAndCarDetails::getCarId,
            LeftJoinCarAndCarDetails.getters());
    }

    static Session cascadeSave(Session session, Car car) {
        car.setName("Hibernate Master Class");
        CarDetails details = new CarDetails();
        car.addDetails(details);

        save(session, car);
        return session;
    }

    static void cascadeMerge(Session session, Car car) {
        car.setName("merged car name");
        car.getDetails().setVisible(true);
        commit(session, Session::merge, car);
    }

    static void cascadeDelete(Session session, Object entity) {
        commit(session, Session::delete, entity);
    }

    static void cascadeOrphanRemove(Session session, Integer id) {
        commit(session, s -> {
            Car car = session.get(Car.class, id);
            car.removeDetails(car.getDetails());
        });
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

