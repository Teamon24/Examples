package equals;

/**
 * Created by Артем on 15.12.2016.
 */


class A {
    private int a;
    public A(int a) {this.a = a;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof A)) return false;

        A a1 = (A) o;

        return a == a1.a;
    }
    @Override
    public int hashCode() {
        return a;
    }
}

public class B extends A {
    private int b;
    public B(int a, int b) {
        super(a);
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof B)) return false;
        if (!super.equals(o)) return false;

        B b1 = (B) o;

        return b == b1.b;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + b;
        return result;
    }

    public static void main(String[] args) {
        A a = new A(1);
        B b = new B(1,2);
        System.out.println(a.equals(b));
        System.out.println(b.equals(a));
    }
}

class Course extends Object {}
class BaseCourse extends Course {}
class FreeCourse extends  BaseCourse {}
class OptionalCourse extends Course {}

class Hierarchy{
    static void logic(Course c){
        if (c instanceof BaseCourse) {
            System.out.println("BaseCourse");
        } else if(c instanceof OptionalCourse) {
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

