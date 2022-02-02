package dbms.hibernate.hbm.entity;

import com.google.common.collect.Sets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.HibernateUtils.getSessionFactory;

public class Demo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = getSessionFactory("/META-INF/hibernate-hbm-entities.cfg.xml");
        Session session = sessionFactory.openSession();

        Passport passport = createPassport();
        Address address = createAddress();
        Company company = createCompany();

        commit(session, (s) -> {
            s.save(company);
            s.save(address);
        });

        Address address2 = createAddress();
        Person person = createPerson(passport, address, company);

        commit(session, () -> {
            session.save(address2);
            session.save(person);
        });

        commit(session, Demo::printAll);
        commit(session, (s) -> {
            Person foundPerson = s.find(Person.class, person.getId());
            foundPerson.remove(address);
            s.delete(foundPerson);
        });

        session.close();
    }

    private static Person createPerson(Passport passport, Address address, Company company) {
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Testoff");
        person.setBirthDate(Timestamp.from(Instant.now()));
        person.setAddress(address);
        person.setPassports(Sets.newHashSet(passport));
        person.setCompanies(Sets.newHashSet(company));

        Set<Person> persons = Sets.newHashSet(person);
        company.setPersons(persons);
        address.setPersons(persons);
        passport.setPerson(person);
        return person;
    }

    private static Company createCompany() {
        Company company = new Company();
        company.setName("Acme Ltd");
        return company;
    }

    private static Address createAddress() {
        Address address = new Address();
        address.setCity("Kickapoo");
        address.setStreet("Main street");
        address.setBuilding("1");
        return address;
    }

    private static Passport createPassport() {
        Passport passport = new Passport();
        passport.setSeries("AS");
        passport.setNo("123456");
        passport.setIssueDate(Timestamp.from(Instant.now()));
        return passport;
    }

    private static void printAll(Session session) {
        session.createQuery("from Person ")
            .list()
            .forEach(System.out::println);
        session.createQuery("from Passport ")
            .list()
            .forEach(System.out::println);
        session.createQuery("from Address ")
            .list()
            .forEach(System.out::println);
        session.createQuery("from Company ")
            .list()
            .forEach(System.out::println);
    }
}
