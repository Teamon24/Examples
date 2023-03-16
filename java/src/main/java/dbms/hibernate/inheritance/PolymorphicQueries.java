package dbms.hibernate.inheritance;

import dbms.hibernate.SessionFactoryBuilder;
import dbms.hibernate.TransactionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.ExceptionUtils;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.save;

public class PolymorphicQueries {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new SessionFactoryBuilder()
            .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
            .entitiesClasses(
                Pet.class, Animal.class,
                Person.class, Employee.class, CEO.class,
                Vehicle.class, Bicycle.class, Truck.class, Car.class,
                Product.class, Book.class, Pen.class)
            .build();

        Session session = sessionFactory.openSession();

        mappedSuperclass(session);
        singleTableWithDiscriminator(session);
        tablePerClass(session);
        joinedTable(session);
    }

    private static void joinedTable(Session session) {
        save(session,
            new Pet().setName("Jerome").setSpecies("cat"),
            new Pet().setName("Jerry").setSpecies("mouse")
        );

        int animals = session
            .createQuery("from Animal")
            .getResultList()
            .size();

        int expectedAmount = 2;
        ExceptionUtils.throwIf(animals != expectedAmount, "Animals size should be " + expectedAmount);
    }

    public static void mappedSuperclass(Session session) {
        save(session,
            new Employee()
                .setCompany("BlobSoft")
                .setName("Blohn Blobovich"),
            new CEO()
                .setDepartment("Rocket Science")
                .setCompany("Pesla")
                .setName("Getlob Pusk")
        );

        int persons = session
            .createQuery("from Employee")
            .getResultList()
            .size();

        int expectedAmount = 2;
        ExceptionUtils.throwIf(persons != expectedAmount, "Persons size should be " + expectedAmount);
    }

    private static void singleTableWithDiscriminator(Session session) {
        commit(session, s -> {
            TransactionUtils.operate(s, Session::save ,
                new Book()
                    .setAuthor("George Orwell")
                    .setName("1984"),

                new Pen()
                    .setColor("blue")
                    .setName("Erich Crause")
            );
        });
        int products = session
            .createQuery("from Product")
            .getResultList()
            .size();

        int expectedAmount = 2;
        ExceptionUtils.throwIf(products != expectedAmount, "Products size should be " + expectedAmount);
    }

    public static void tablePerClass(Session session) {
        save(session,
            new Vehicle().setManufacturer("BMW"),
            new Bicycle().setSpeedsQuantity(4).setManufacturer("T-traugh"),
            new Truck().setSeatsQuantity(4).setManufacturer("Tyuwaha"),
            new Car().setCapacity(500).setManufacturer("Ferrari")
        );

        int persons = session
            .createQuery("from Vehicle")
            .getResultList()
            .size();

        int expectedAmount = 4;
        ExceptionUtils.throwIf(persons != expectedAmount, "Vehicles size should be " + expectedAmount);
    }
}
