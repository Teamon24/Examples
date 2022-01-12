package core.variance;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

class A {}
class B extends A {}
class C extends B {}

class Source<T> {
    T value;
    public void consume(T ... values) { Arrays.stream(values).forEach(System.out::println); }
    public T produce() { return this.value; }
}

public class Demo {
    public static void main(String[] args) {
        Source<A> consumerA = new Source<>();
        Source<B> consumerB = new Source<>();
        Source<C> consumerC = new Source<>();


        ///////////////////////////////
        //       Covariance         // extends => produce
        /////////////////////////////
        List<Source<? extends A>> belowA = List.of(new Source<A>(), new Source<B>(), new Source<C>());
        List<Source<? extends B>> belowB = List.of(                 new Source<B>(), new Source<C>());
        List<Source<? extends C>> belowC = List.of(                                  new Source<C>());

        //Всe, что производит типы ниже A, B, C может быть только под ссылкой A;
        belowA.forEach(source -> { A a = source.produce(); consumerA.consume(a); });

        //Всe, что производит типы ниже B, C может быть только под ссылкой B;
        belowB.forEach(source -> { B b = source.produce(); consumerB.consume(b); });

        //Всe, что производит типы ниже C, может быть только под ссылкой C;
        belowC.forEach(source -> { C c = source.produce(); consumerC.consume(c); });


        ///////////////////////////////
        //      Contravariance      // super => consume
        /////////////////////////////
        List<Source<? super A>> aboveA = List.of(                                  new Source<A>(), new Source<Object>());
        List<Source<? super B>> aboveB = List.of(                 new Source<B>(), new Source<A>(), new Source<Object>());
        List<Source<? super C>> aboveC = List.of(new Source<C>(), new Source<B>(), new Source<A>(), new Source<Object>());

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.forEach(source -> source.consume(new A(), new B(), new C()));

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.forEach(source -> source.consume(         new B(), new C()));

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.forEach(source -> source.consume(                  new C()));
    }
}


