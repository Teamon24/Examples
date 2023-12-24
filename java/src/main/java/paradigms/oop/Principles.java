package paradigms.oop;

public interface Principles {}

/**
 * <p><strong>Abstraction</strong> aims to hide complexity from users and show them only relevant information. For example, if you’re driving a car, you don’t need to know about its internal workings.
 *
 * <p>The same is true of Java classes. You can hide internal implementation details using abstract classes or interfaces. On the abstract level, you only need to define the method signatures (name and parameter list) and let each class implement them in their own way.
 *
 * <p>Abstraction in <strong>Java</strong>:
 * <li>Hides the underlying complexity of data
 * <li>Helps avoid repetitive code
 * <li>Presents only the signature of internal functionality
 * <li>Gives flexibility to programmers to change the implementation of abstract behavior
 * <li><strong>Partial abstraction</strong> (0-100%) can be achieved with <strong>abstract classes</strong>
 * <li><strong>Total abstraction</strong> (100%) can be achieved with <strong>interfaces</strong>
 */
interface Abstraction {}

/**
 * <p><strong>Encapsulation</strong> helps with data security, allowing you to protect the data stored in a class from system-wide access. As the name suggests, it safeguards the internal contents of a class like a capsule.
 *
 * <p>You can implement encapsulation in Java by making the fields (class variables) private and accessing them via their public getter and setter methods. JavaBeans are examples of fully encapsulated classes.
 *
 *
 * <p>Encapsulation in <strong>Java</strong>:
 *
 * <li>Restricts direct access to data members (fields) of a class
 * <li>Fields are set to private
 * <li>Each field has a getter and setter method
 * <li>Getter methods return the field
 * <li>Setter methods let us change the value of the field
 */
interface Encapsulation {}

/**
 * <p><strong>Inheritance</strong> makes it possible to create a child class that inherits the fields and methods of the parent class. The child class can override the values and methods of the parent class, but it’s not necessary. It can also add new data and functionality to its parent.
 *
 * <p>Parent classes are also called superclasses or base classes, while child classes are known as subclasses or derived classes as well. Java uses the extends keyword to implement the principle of inheritance in code.
 *
 * <p>Inheritance in <strong>Java</strong>:
 *
 * <li>A class (child class) can extend another class (parent class) by inheriting its features
 * <li>Implements the <strong>DRY (Don’t Repeat Yourself)</strong> programming principle
 * <li>Improves code reusability
 * <li>Multi-level inheritance is allowed in Java (a child class can have its own child class as well)
 * <li>Multiple inheritances are not allowed in Java (a class can’t extend more than one class)
 */
interface Inheritance {}

/**
 * <p><strong>Polymorphism</strong> refers to the ability to perform a certain action in different ways. In Java, polymorphism can take two forms: method overloading and method overriding.
 *
 * <p>Method overloading happens when various methods with the same name are present in a class. When they are called, they are differentiated by the number, order, or types of their parameters. Method overriding occurs when a child class overrides a method of its parent.
 *
 * <p>Polymorphism in <strong>Java</strong>:
 *
 * <li>The same method name is used several times
 * <li>Different methods of the same name can be called from an object
 * <li>All Java objects can be considered polymorphic (at the minimum, they are of their own type and instances of the Object class)
 * <li><strong>Static polymorphism</strong> in Java is implemented by method <strong>overloading</strong>
 * <li><strong>Dynamic polymorphism</strong> in Java is implemented by method <strong>overriding</strong>
 */
interface Polymorphism {}
