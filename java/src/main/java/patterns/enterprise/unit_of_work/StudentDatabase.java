package patterns.enterprise.unit_of_work;

public final class StudentDatabase implements Database<Student> {

    @Override
    public void insert(Student student) {
        System.out.println("Inserting: " + student.toString());
    }

    @Override
    public void modify(Student student) {
        System.out.println("Modifying: " + student.toString());
    }

    @Override
    public void delete(Student student) {
        System.out.println("Deleting: " + student.toString());
    }
}