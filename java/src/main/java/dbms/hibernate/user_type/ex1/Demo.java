package dbms.hibernate.user_type.ex1;

import com.github.javafaker.Faker;
import dbms.hibernate.HibernateUtils;
import dbms.hibernate.TransactionUtils;
import dbms.hibernate.user_type.ex1.type.AddressType;
import dbms.hibernate.user_type.ex1.type.LocalDateStringType;
import dbms.hibernate.user_type.ex1.type.PhoneNumberType;
import dbms.hibernate.SessionFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

import static dbms.hibernate.TransactionUtils.commit;

public interface Demo {
    static void main(String[] args) {

        SessionFactory sessionFactory =
            new SessionFactoryBuilder()
                .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
                .entitiesClasses(OfficeEmployee.class)
                .basicTypes(LocalDateStringType.INSTANCE)
                .userTypes(PhoneNumberType.INSTANCE)
                .compositeUserTypes(AddressType.INSTANCE)
                .build();

        Session session = sessionFactory.openSession();

        try(sessionFactory) {

            TransactionUtils.commit(session, s -> {

                OfficeEmployee employee = new OfficeEmployee();
                employee.setHiredAt(LocalDate.now());
                employee.setSalary(new Salary(100_000, "RUB"));
                employee.setPhoneNumber(new PhoneNumber(7, 937, 200_64_86));

                Faker faker = Faker.instance();

                employee.setAddress(
                    new Address(
                        faker.address().fullAddress(),
                        faker.address().secondaryAddress(),
                        faker.address().country(),
                        faker.address().cityName(),
                        faker.address().zipCode()));
                s.save(employee);
            });

            HibernateUtils
                .findAll(session, OfficeEmployee.class)
                .forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("session is closed: " + !session.isOpen());
            System.out.println("sessionFactory is closed: " + sessionFactory.isClosed());
        }
    }
}
