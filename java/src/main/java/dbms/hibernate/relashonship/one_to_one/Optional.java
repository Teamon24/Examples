package dbms.hibernate.relashonship.one_to_one;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * <ul>It's one-to-one, but sometimes an employee might not have a workstation and vice versa.</ul>
 *
 * <pre>
 *  ___________     ______________________     ______________
 * | employees |   | employee_workstation |   | workstations |
 * |___________|   |______________________|   |______________|
 * |           |   | workstation_id-------|-->| id           |
 * | id        |<--|-employee_id          |   | number       |
 * | name      |   |                      |   | floor        |
 *
 * </pre>
 */
public interface Optional {
}

@Entity
@Table(name = "employees")
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_workstation",
        joinColumns =
            {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
        inverseJoinColumns =
            {@JoinColumn(name = "workstation_id", referencedColumnName = "id")})
    private WorkStation workStation;
}

@Entity
@Table(name = "workstations")
class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "workStation")
    private Employee employee;
}
