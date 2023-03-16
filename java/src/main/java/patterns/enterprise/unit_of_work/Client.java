package patterns.enterprise.unit_of_work;

import patterns.enterprise.unit_of_work.abstraction.UnitOfWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {

    /**
     * @param args no argument sent
     */
    public static void main(String[] args) {

        HashMap<UnitOfWork.OperationType, List<Student>> context = new HashMap<>();
        StudentUnitOfWork studentUnitOfwork =
            new StudentUnitOfWork(context, new StudentDatabase(), new StudentIdentityMap());

        Student last = studentUnitOfwork.getLast();

        List<Student> newStudents = getNewStudents(last.getId());

        newStudents.forEach(studentUnitOfwork::registerNew);
        List<Student> studentsToModify = newStudents.subList(0, newStudents.size() / 2);

        studentsToModify.forEach(student -> {
            student.setAddress("Somewhere, where is quite");
            studentUnitOfwork.registerModified(student);
        });

        Student studentToRemove = studentUnitOfwork.get(1);
        studentUnitOfwork.registerDeleted(studentToRemove);

        studentUnitOfwork.commit();
    }

    private static List<Student> getNewStudents(Integer lastId) {
        Student ram = new Student(lastId + 1, "Ram", "Street 9, Cupertino");
        Student shyam = new Student(lastId + 2, "Shyam", "Z bridge, Pune");
        Student gopi = new Student(lastId + 3, "Gopi", "Street 10, Mumbai");
        Student pier = new Student(lastId + 4, "Pier", "Street 12, France");
        Student petr = new Student(lastId + 5, "Petr", "Street 10, Russia");
        Student adolf = new Student(lastId + 6, "Adolf", "Street 10, Germany");

        List<Student> newStudents = new ArrayList<>();
        newStudents.add(ram);
        newStudents.add(shyam);
        newStudents.add(gopi);
        newStudents.add(pier);
        newStudents.add(petr);
        newStudents.add(adolf);
        return newStudents;
    }
}