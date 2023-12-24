package architecture.patterns.enterprise.unit_of_work;

import java.util.ArrayList;
import java.util.List;

public class Client {

    /**
     * @param args no argument sent
     */
    public static void main(String[] args) {
        StudentUnitOfWork studentUnitOfWork =
            new StudentUnitOfWork(new StudentDatabase(), new StudentIdentityMap());

        List<Student> newStudents = getNewStudents(studentUnitOfWork);

        studentUnitOfWork.registerNew(newStudents);
        studentUnitOfWork.registerModified(modify(getStudentsToModify(newStudents)));
        studentUnitOfWork.registerDeleted(studentUnitOfWork.getFirst());
        studentUnitOfWork.commit();
    }

    private static List<Student> modify(List<Student> studentsToModify) {
        studentsToModify.forEach(it -> it.setAddress("Somewhere, where is quite"));
        return studentsToModify;
    }

    private static List<Student> getNewStudents(StudentUnitOfWork studentUnitOfWork) {
        Integer lastId = studentUnitOfWork.getLast().getId();
        Student ram   = new Student(lastId + 1, "Ram", "Street 9, Cupertino");
        Student shyam = new Student(lastId + 2, "Shyam", "Z bridge, Pune");
        Student gopi  = new Student(lastId + 3, "Gopi", "Street 10, Mumbai");
        Student pier  = new Student(lastId + 4, "Pier", "Street 12, France");
        Student petr  = new Student(lastId + 5, "Petr", "Street 10, Russia");
        Student adolf = new Student(lastId + 6, "Adolf", "Street 10, Germany");
        return new ArrayList<>(List.of(ram, shyam, gopi, pier, petr, adolf));
    }

    private static List<Student> getStudentsToModify(List<Student> newStudents) {
        return newStudents.subList(0, newStudents.size() / 2);
    }
}