package dbms.hibernate.hbm.entity;

import com.github.javafaker.Faker;
import com.google.common.collect.Sets;
import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import dbms.hibernate.TransactionUtils;
import utils.RandomUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static dbms.hibernate.TransactionUtils.commit;

public class Demo {

    public static final Faker FAKER = Faker.instance();

    public static void main(String[] args) {
        SessionFactory sessionFactory =
            new SessionFactoryBuilder().resourceName("/META-INF/hibernate-hbm-entities.cfg.xml").build();

        Session session = sessionFactory.openSession();

        Passport passport = createPassport();
        Address address = createAddress();
        Company company = createCompany();
        Address address2 = createAddress();
        Person person = createPerson(passport, address, company);

        TransactionUtils.commit(session, (s) -> {
            s.save(company);
            s.save(address);
            s.save(address2);
            s.save(person);
        });

        session = HibernateUtils.reopen(session, sessionFactory);

        HibernateUtils.findAll(session, Person.class).forEach(System.out::println);
        HibernateUtils.findAll(session, Passport.class).forEach(System.out::println);
        HibernateUtils.findAll(session, Address.class).forEach(System.out::println);
        HibernateUtils.findAll(session, Company.class).forEach(System.out::println);

        TransactionUtils.commit(session, (s) -> {
            Person foundPerson = s.find(Person.class, person.getId());
            foundPerson.remove(address);
            s.delete(foundPerson);
        });

        session.close();
    }

    private static Person createPerson(Passport passport, Address address, Company company) {
        Person person = new Person();
        person.setFirstName(FAKER.name().firstName());
        person.setLastName(FAKER.name().lastName());
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
        company.setName(FAKER.company().name());
        return company;
    }

    private static Address createAddress() {
        Address address = new Address();
        address.setCity(FAKER.address().city());
        address.setStreet(FAKER.address().streetName());
        address.setBuilding(FAKER.address().buildingNumber());
        return address;
    }

    private static Passport createPassport() {
        Passport passport = new Passport();
        passport.setSeries(randomSeries());
        passport.setNo(randomNumber());
        passport.setIssueDate(Timestamp.from(Instant.now()));
        return passport;
    }

    private static String randomNumber() {
        Integer random = RandomUtils.random(100000, 999999);
        String number = random.toString();
        return number;
    }

    private static String randomSeries() {
        String[] split = UUID.randomUUID().toString().split("-");
        String series = split[0]+"-"+split[1];
        return series;
    }
}
