package abstract_class; /**
 * Created by Артем on 18.12.2016.
 */
public class Test {
    public static void main(String[] args) {
//        A a  = new A();
//        A a1 = new A(2);
//        A a2 = new A() {
//            @Override
//            int a() {
//                return 0;
//            }
//        };

        A a3 = new A(1) {
            @Override
            int a() {
                return 0;
            }

            @Override
            int b(int b) {
                return 0;
            }
        };

        a3.a();
        a3.b(2);
    }
}
