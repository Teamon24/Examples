package classes;

/**
 * .  .    .
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class Outer {
    public int _public;
    private int _private;
    public static int public_static = InnerStatic.public_static;;
    private static int private_static = InnerStatic.private_static;

    public int _public_1 = InnerStatic.public_static;
    public int _public_2 = InnerStatic.private_static;
    public int _public_3 = new InnerStatic()._public;
    public int _public_5 = new Inner()._public;

    public int _private_4 = new InnerStatic()._private;
    public int _private_6 = new Inner()._private;

    public static void static_method() {
        System.out.println("Static method of outer class");
    }

    public int[] method() {
        return new int[0];
    }

    //NESTED_CLASS
    public static class InnerStatic {
        public int _public = new Outer()._public;
        private static int private_static = Outer.private_static;

        private int _private = new Outer()._private;
        public static int public_static = Outer.public_static;

        public static void static_method() {
            System.out.println("Static method of nested static class");
        }

        public void method() {
            System.out.println("Non-static method of nested static class");
        }
    }

    public class Inner {
        /*public static int  _static;*/  //Compiler error. Only non-static elements
        private int _private = new Outer()._private;
        public int i4 = new InnerStatic()._private;
        public int i2 = InnerStatic.private_static;
        private int _private_static = Outer.private_static;

        public int i1 = InnerStatic.public_static;
        public int _public = new Outer()._public;
        public int i3 = new InnerStatic()._public;

        public void method() {
            System.out.println("Non-static method of inner(non-static nested) class");
        }
    }
}

class Test {
    public static void main(String[] args) {
        /*Capt'n: There is no access to private fields.*/

        Outer.public_static = 1;
        Outer outer = new Outer();
        outer._public = 1;

        Outer.InnerStatic.public_static = 1;
        Outer.InnerStatic innerStatic = new Outer.InnerStatic();
        innerStatic._public = 1;
        innerStatic.method();
        Outer.InnerStatic.static_method();


        Outer.Inner inner = new Outer().new Inner();
        inner._public = 1;
        inner.method();
    }
}

class Descent extends Outer {
    public static void static_method() {
        Outer.static_method();
        System.out.println("Static method of outer class");
    }

    @Override
    public int[] method() {

        super.method();
        int[] is = new int[6];
        is[0] = super._public_1;
        is[1] =  this._public_1;
        is[2] = super._public_2;
        is[3] =  this._public_2;
        is[4] = super._public_3;
        is[5] =  this._public_3;
        return is;
    }
}
