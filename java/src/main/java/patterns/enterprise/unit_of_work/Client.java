package patterns.enterprise.unit_of_work;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static patterns.enterprise.unit_of_work.UnitOfWork.*;

public class Client {

    /**
     * @param args no argument sent
     */
    public static void main(String[] args) {
        Student ram = new Student(1, "Ram", "Street 9, Cupertino");
        Student shyam = new Student(2, "Shyam", "Z bridge, Pune");
        Student gopi = new Student(3, "Gopi", "Street 10, Mumbai");
        Student pier = new Student(4, "Pier", "Street 12, France");
        Student petr = new Student(5, "Petr", "Street 10, Russia");
        Student adolf = new Student(6, "Adolf", "Street 10, Germany");

        HashMap<OperationType, List<Student>> context = new HashMap<>();
        StudentUnitOfWork studentUnitOfwork =
            new StudentUnitOfWork(context, new StudentDatabase());

        Stream.of(ram, pier, petr, adolf)
            .forEach(studentUnitOfwork::registerNew);

        studentUnitOfwork.registerModified(shyam);
        studentUnitOfwork.registerDeleted(gopi);

        studentUnitOfwork.commit();
    }
}