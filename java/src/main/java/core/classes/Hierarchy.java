package core.classes;

public class Hierarchy {
    static void logic(Course c) {
        if (c instanceof BaseCourse) {
            System.out.println("BaseCourse");
        } else if (c instanceof OptionalCourse) {
            System.out.println("OptionalCourse");
        } else {
            System.out.println(c.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) {
        Hierarchy.logic(new BaseCourse());
        Hierarchy.logic(new OptionalCourse());
        Hierarchy.logic(new FreeCourse());
        Course c = new FreeCourse();
        Hierarchy.logic(c);
    }
}
class Course extends Object {}
class BaseCourse extends Course {}
class FreeCourse extends BaseCourse {}

class OptionalCourse extends Course {}
