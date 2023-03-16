package patterns.enterprise.unit_of_work;

import patterns.enterprise.unit_of_work.abstraction.Database;
import utils.PrintUtils;

import java.util.Collection;
import java.util.HashMap;

import static java.lang.System.out;

public final class StudentDatabase implements Database<Integer, Student> {

    private final HashMap<Integer, Student> entities = new HashMap<>() {{
       put(1, new Student(1, "Golde Brown", "Some where"));
       put(2, new Student(2, "Christian Glay", "Some where"));
       put(3, new Student(3, "Robert Trupet", "Some where"));
       put(4, new Student(4, "Bill Waits", "Some where"));
       put(5, new Student(5, "Samantha Igle", "Some where"));
       put(6, new Student(6, "Craig Dandolin", "Some where"));
    }};

    @Override
    public void insert(Student student) {
        System.out.println("Inserting: " + student.toString());
        Integer id = student.getId();
        if (id == null) {
            this.entities.put(findLast().getId() + 1, student);
        }
    }

    @Override
    public void modify(Student student) {
        this.entities.put(student.getId(), student);
        System.out.println("Modifying: " + student);
    }

    @Override
    public void delete(Student student) {
        this.entities.remove(student.getId(), student);
        System.out.println("Deleting: " + student);
    }

    @Override
    public Collection<Student> findAll() {
        return this.entities.values();
    }

    @Override
    public Student find(Integer id) {
        Student student = this.entities.get(id);
        System.out.println("Found: " + student.toString());
        return student;
    }
}