package core.classes.abstrct; /**
 * Created by Артем on 18.12.2016.
 */
public abstract class A {
    int a;
    abstract int a();
    abstract int b(int b);
    A(){}
    A (int a){
        this.a = a;
    }
}
