package architecture;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import utils.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>In Java, we can reuse our code using an <strong>Is-A</strong> relationship or using a <strong>Has-A</strong> relationship. An <strong>Is-A</strong> relationship is also known as inheritance and a <strong>Has-A</strong> relationship is also known as <strong>composition</strong> in Java.
 *
 * <p><strong>Is-A</strong> Relationship in Java
 * <ul><li>In Java, an <strong>Is-A</strong> relationship depends on inheritance. Further inheritance is of two types, class and interface inheritance. <strong>It is used for code reusability</strong> in Java. One of the properties of inheritance is that inheritance is unidirectional in nature. Like we can say that a house is a building. But not all buildings are houses. We can easily determine an <strong>Is-A</strong> relationship in Java. When there is an <i>extends</i> or <i>implement</i> keyword in the class declaration in Java, then the specific class is said to be following the <strong>Is-A</strong> relationship.</li></ul>
 *
 * <p><strong>Has-A</strong> Relationship in Java
 * <ul><li>In Java, a <strong>Has-A</strong> relationship is also known as <strong>composition</strong>. <strong>It is also used for code reusability</strong> in Java. In Java, a <strong>Has-A</strong> relationship simply means that an instance of one class has a reference to an instance of another class or an other instance of the same class. In Java, there is no such keyword that implements a <strong>Has-A</strong> relationship. But we mostly use new keywords to implement a <strong>Has-A</strong> relationship in Java.</li></ul>
 *
 * <p>Usages
 * <ul><li>when we use inheritance then we bind a class on other class contract</li>
 * <li>when we use association then we don't bind a class on other class contract</li>
 */
interface IsHas {
    @AllArgsConstructor
    class Bike {
        private Engine engine;
        public void start() {
            engine.start();
            ConcurrencyUtils.sleep(1);
            engine.stop();
        }
    }

    class Pulsar extends Bike {
        public Pulsar(Engine engine) { super(engine); }
    }

    class Engine {
        public void start() {
            String name = this.getClass().getSimpleName();
            System.out.println(name + " started");
        }

        public void stop() {
            String name = this.getClass().getSimpleName();
            System.out.println(name + " stopped");
        }
    }

    static void main(String[] args) { new Pulsar(new Engine()).start(); }
}

/**
 * <pre>
 * {@code
 * |---------------------------|
 * |   Association             |
 * |------------------------   |
 * |   Aggregation         |   |
 * |-----------------|     |   |
 * |   Composition   |     |   |
 * |-----------------|     |   |
 * |-----------------------    |
 * |---------------------------|}
 * </pre>
 */
public interface Associations {}

/**
 * <p>Association is a relation between two separate classes which establishes through their Objects. Association can be <strong>one-to-one</strong>, <strong>one-to-many</strong>, <strong>many-to-one</strong>, <strong>many-to-many</strong>. In Object-Oriented programming, an Object communicates to another object to use functionality and services provided by that object.
 * <p><strong>Composition</strong> and <strong>Aggregation</strong> are the two forms of association.
 */
interface Association {
    static void main(String[] args) {
        Employee emp = new Employee("Ridhi");
        Bank bank = new Bank("ICICI", Set.of(emp));
        for (Employee employee : bank.employees) {
            System.out.println(employee.getName() + " belongs to " + bank.getName());
        }
    }

    @AllArgsConstructor
    @Getter
    class Bank {
        private String name;
        @Setter private Set<Employee> employees;
    }

    @AllArgsConstructor
    class Employee { @Getter private String name; }
}

/**
 * <p>Special form of Association where:
 *
 * <ul>
 * <li>It represents <strong>Has-A</strong> relationship.</li>
 * <li>It is a unidirectional association i.e. a one-way relationship. For example, a department can have students but vice versa is not possible and thus unidirectional in nature.</li></ul>
 * </p>In <strong>Aggregation</strong>, both entries can survive individually which means <strong>ending one entity will not affect the other entity</strong>.
 */
interface Aggregation {
    static void main(String[] args) {
        val institute = new Institute()
            .department("CSE",
                new Student("Mia", "CSE", 1),
                new Student("Priya", "CSE", 2))
            .department("EE",
                new Student("John", "EE", 1),
                new Student("Rahul", "EE", 2));

        System.out.print("Total students in institute: ");
        System.out.print(institute.getTotalStudentsInInstitute());
    }

    @AllArgsConstructor
    class Student {
        String name, dept;
        int id;
    }

    @AllArgsConstructor
    @Getter
    class Department {
        String name;
        private List<Student> students;
    }

    @NoArgsConstructor
    @Getter
    class Institute {
        String instituteName;
        private List<Department> departments;

        public Integer getTotalStudentsInInstitute() {
            return departments.stream()
                .map(Department::getStudents)
                .map(List::size)
                .reduce(Integer::sum)
                .get();
        }

        public Institute department(String name, Student... students) {
            if (departments.isEmpty()) departments = new ArrayList<>();
            this.departments.add(new Department(name, Arrays.asList(students)));
            return this;
        }
    }
}

/**
 * <p>Restricted form of Aggregation in which two entities are highly dependent on each other.
 *
 * <p>It represents <strong>Part-Of</strong> relationship.
 * <p>In <strong>Composition</strong>, both entities are dependent on each other.
 * When there is a composition between two entities, the <strong>composed object cannot exist without the other entity</strong>.
 */
interface Composition {

    static void main(String[] args) {
        Book b1 = new Book("EffectiveJ Java", "Joshua Bloch");
        Book b2 = new Book("Thinking in Java", "Bruce Eckel");
        Book b3 = new Book("Java: The Complete Reference", "Herbert Schildt");

        Library library = new Library(List.of(b1, b2, b3));
        for (Book bk : library.getBooks()) {
            System.out.println("Title : " + bk.title + " and Author : " + bk.author);
        }
    }

    @AllArgsConstructor
    class Book {
        public String title;
        public String author;
    }

    @AllArgsConstructor
    @Getter
    class Library { private final List<Book> books; }
}


