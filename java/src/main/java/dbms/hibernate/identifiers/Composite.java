package dbms.hibernate.identifiers;

/**
 * <pre>
 *  _________________     _________________
 * | employee        |   | phone          |
 * |_________________|   |________________|
 * | company_id      |   | number         |
 * | employee_number |--<| compamy_id     |
 * | name            |   | employee_umber |
 * </pre>
 *
 * <p>In the diagram above, the employee table has a Composite Key, which consists of two columns:
 *
 * <ul>
 * <li>company_id</li>
 * <li>employee_number</li>
 * </ul>
 *
 * <p>Every Employee can also have a Phone, which uses the same composite key to reference its owning Employee.
 *
 * <p>Composite Primary Key with JPA and Hibernate
 * <p>To map this database table mapping, we need to isolate the compound key into an @Embeddable first:
 * <pre>{@code
 * @Embeddable
 * public class EmployeeId implements Serializable {
 *
 *     @Column(name = "company_id")
 *     private Long companyId;
 *
 *     @Column(name = "employee_number")
 *     private Long employeeNumber;

 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (!(o instanceof EmployeeId)) return false;
 *         EmployeeId that = (EmployeeId) o;
 *         return Objects.equals(getCompanyId(), that.getCompanyId()) &&
 *                 Objects.equals(getEmployeeNumber(), that.getEmployeeNumber());
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(getCompanyId(), getEmployeeNumber());
 *     }
 * }}</pre>
 * <p>The JPA specification says that all entity identifiers should be serializable and implement equals and hashCode.
 *
 * <p>So, an Embeddable that is used as a composite identifier must be Serializable and implement equals and hashCode.
 *
 * <p>The Employee mapping looks as follows:
 *<pre>{@code
 * @Entity(name = "Employee")
 * @Table(name = "employee")
 * public class Employee {
 *
 *     @EmbeddedId
 *     private EmployeeId id;
 *
 *     private String name;
 * }}</pre>
 * The @EmbeddedId is used to instruct Hibernate that the Employee entity uses a compound key.
 *
 * The Phone mapping is rather straightforward as well:
 * <pre>{@code
 * @Entity(name = "Phone")
 * @Table(name = "phone")
 * public class Phone {
 *
 *     @Id
 *     @Column(name = "`number`")
 *     private String number;
 *
 *     @ManyToOne
 *     @JoinColumns({
 *         @JoinColumn(
 *             name = "company_id",
 *             referencedColumnName = "company_id"),
 *         @JoinColumn(
 *             name = "employee_number",
 *             referencedColumnName = "employee_number")
 *     })
 *     private Employee employee;
 * }
 * </pre>
 * <p>The Phone uses the number as an entity identifier since every phone number and the @ManyToOne mapping uses the two columns that are part of the compound key.
 * <p></p>
 * <p></p>
 * <p>Testing time
 * <p>To see how it works, consider the following persistence logic:
 * <pre>{@code
 * doInJPA(entityManager -> {
 *     Employee employee = new Employee();
 *     employee.setId(new EmployeeId(1L, 100L));
 *     employee.setName("Vlad Mihalcea");
 *     entityManager.persist(employee);
 * });
 *
 * doInJPA(entityManager -> {
 *     Employee employee = entityManager.find(
 *         Employee.class, new EmployeeId(1L, 100L));
 *     Phone phone = new Phone();
 *     phone.setEmployee(employee);
 *     phone.setNumber("012-345-6789");
 *     entityManager.persist(phone);
 * });
 *
 * doInJPA(entityManager -> {
 *     Phone phone = entityManager.find(Phone.class, "012-345-6789");
 *     assertNotNull(phone);
 *     assertEquals(new EmployeeId(1L, 100L), phone.getEmployee().getId());
 * }); }</pre>
 *
 * <p>Which generates the following SQL statements:
 * <pre>{@code
 * INSERT INTO employee (name, company_id, employee_number)
 * VALUES ('Vlad Mihalcea', 1, 100)
 *
 * SELECT e.company_id AS company_1_0_0_ ,
 *        e.employee_number AS employee2_0_0_ ,
 *        e.name AS name3_0_0_
 * FROM   employee e
 * WHERE  e.company_id = 1
 *        AND e.employee_number = 100
 *
 * INSERT INTO phone (company_id, employee_number, `number`)
 * VALUES (1, 100, '012-345-6789')
 *
 * SELECT p.number AS number1_1_0_ ,
 *        p.company_id AS company_2_1_0_ ,
 *        p.employee_number AS employee3_1_0_ ,
 *        e.company_id AS company_1_0_1_ ,
 *        e.employee_number AS employee2_0_1_ ,
 *        e.name AS name3_0_1_
 * FROM   phone p
 * LEFT OUTER JOIN employee e
 * ON     p.company_id = e.company_id AND p.employee_number = e.employee_number
 * WHERE  p.number = '012-345-6789'
 * }</pre>
 *
 * <p>Mapping relationships using the Composite Key
 * <p>We can even map relationships using the information provided within the Composite Key itself. In this particular example, the company_id references a Company entity which looks as follows:
 * <pre>{@code
 * @Entity(name = "Company")
 * @Table(name = "company")
 * public class Company {
 *
 *     @Id
 *     private Long id;
 *
 *     private String name;

 *
 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (!(o instanceof Company)) return false;
 *         Company company = (Company) o;
 *         return Objects.equals(getName(), company.getName());
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(getName());
 *     }
 * } }</pre>
 * We can have the Composite Key mapping referencing the Company entity within the Employee entity:
 * <pre>{@code
 * @Entity(name = "Employee")
 * @Table(name = "employee")
 * public class Employee {
 *
 *     @EmbeddedId
 *     private EmployeeId id;
 *
 *     private String name;
 *
 *     @ManyToOne
 *     @JoinColumn(name = "company_id",insertable = false, updatable = false)
 *     private Company company;
 * }}</pre>
 * <p>Notice that the @ManyToOne association instructs Hibernate to ignore inserts and updates issued on this mapping since the company_id is controlled by the @EmbeddedId.
 *
 * <p>Mapping a relationships inside @Embeddable
 * <p>But that’s not all. We can even move the @ManyToOne inside the @Embeddable itself:
 *
 * <pre>{@code
 * @Embeddable
 * public class EmployeeId implements Serializable {
 *
 *     @ManyToOne
 *     @JoinColumn(name = "company_id")
 *     private Company company;
 *
 *     @Column(name = "employee_number")
 *     private Long employeeNumber;

 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (!(o instanceof EmployeeId)) return false;
 *         EmployeeId that = (EmployeeId) o;
 *         return Objects.equals(getCompany(), that.getCompany()) &&
 *                 Objects.equals(getEmployeeNumber(), that.getEmployeeNumber());
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(getCompany(), getEmployeeNumber());
 *     }
 * }}</pre>
 * <p>Now, the Employee mapping will no longer require the extra @ManyToOne association since it’s offered by the entity identifier:
 *
 * <pre>{@code
 * @Entity(name = "Employee")
 * @Table(name = "employee")
 * public class Employee {
 *
 *     @EmbeddedId
 *     private EmployeeId id;
 *
 *     private String name;
 * }}</pre>
 * <p>The persistence logic changes as follows:
 *
 * <pre>{@code
 * Company company = doInJPA(entityManager -> {
 *     Company _company = new Company();
 *     _company.setId(1L);
 *     _company.setName("vladmihalcea.com");
 *     entityManager.persist(_company);
 *     return _company;
 * });
 *
 * doInJPA(entityManager -> {
 *     Employee employee = new Employee();
 *     employee.setId(new EmployeeId(company, 100L));
 *     employee.setName("Vlad Mihalcea");
 *     entityManager.persist(employee);
 * });
 *
 * doInJPA(entityManager -> {
 *     Employee employee = entityManager.find(
 *         Employee.class,
 *         new EmployeeId(company, 100L)
 *     );
 *     Phone phone = new Phone();
 *     phone.setEmployee(employee);
 *     phone.setNumber("012-345-6789");
 *     entityManager.persist(phone);
 * });
 *
 * doInJPA(entityManager -> {
 *     Phone phone = entityManager.find(Phone.class, "012-345-6789");
 *     assertNotNull(phone);
 *     assertEquals(new EmployeeId(company, 100L), phone.getEmployee().getId());
 * });
 * }</pre>
 */
public interface Composite {
}
