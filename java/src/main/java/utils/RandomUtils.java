package utils;

import com.google.common.collect.ObjectArrays;
import core.collection.benchmark.utils.Sequence;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import static utils.ClassUtils.joinSimpleNames;
import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;

public final class RandomUtils {

    public static final Random random = new Random();

    public static int random(int fromInclusive, int toExclusive) {
        return fromInclusive + (int) (Math.random() * ((toExclusive - fromInclusive)));
    }

    public static Object[] random(int fromInclusive, int toExclusive, int size) {
        Object[] objects = new Object[size];

        for (int i = 0; i < size; i++) {
            objects[i] = random(fromInclusive, toExclusive);
        }

        return objects;
    }

    public static <E> E[][] array2D(Class<E> clazz, int size, Supplier<E> create) {
        E[][] es = (E[][]) Array.newInstance(clazz, size, size);
        for (int i = 0; i < size; i++) {
            es[i] = array(clazz, size, create);
        }
        return es;
    }

    public static <E> E[] array(Class<E> clazz, int size, Supplier<E> creation) {
        E[] es = ObjectArrays.newArray(clazz, size);
        for (int i = 0; i < size; i++) {
            es[i] = creation.get();
        }
        return es;
    }

    public static <E> int randomIndexFrom(Collection<E> collection) {
        return random.nextInt(collection.size());
    }

    public static <E> E randomFrom(Collection<E> collection) {
        int index = randomIndexFrom(collection);
        return byForeach(collection, index);
    }

    private static <E> E byIterator(Collection<E> collection, int index) {
        Iterator<E> iterator = collection.iterator();
        for (int i = 0; i <= index && iterator.hasNext(); i++) {
            if (i == index) {
                return iterator.next();
            }
        }
        throw new RuntimeException("Random element was not found");
    }

    private static <E> E byForeach(Collection<E> collection, int index) {
        ArrayList<E> es = new ArrayList<>(collection);
        for (int i = 0; i < es.size(); i++) {
            if (i == index) {
                return es.get(index);
            }
        }
        throw new RuntimeException("Random element was not found");
    }

    public static <E> E randomFrom(E...es) {
        List<E> collect = new ArrayList<>();
        collect.addAll(Arrays.asList(es));
        E e = randomFrom(collect);
        return e;
    }

    public static <T> T randomFrom(List<Class<? extends T>> classes, Function<Class<?>, T> constructor) {
        Class<?> randomClass = randomFrom(classes);
        T t = constructor.apply(randomClass);
        if (t != null) {
            printfln("Random object type (%s) from classes: %s", simpleName(t), joinSimpleNames(classes));
            return t;
        }
        throw new RuntimeException("");
    }

    public static <Ancestor, Descendant extends Ancestor> Descendant randomDescendant(
        Class<? extends Ancestor> ancestorClass,
        List<Class<? extends Ancestor>> hierarchy,
        Function<Class<?>, Descendant> createObject
    ) {
        List<Class<? extends Descendant>> classes = new ArrayList<>();
        boolean dropping = true;
        for (Class<?> it : hierarchy) {
            if (dropping) {
                if (!it.equals(ancestorClass)) {
                    continue;
                }
                dropping = false;
            }
            classes.add((Class<? extends Descendant>) it);
        }
        printfln("Types (%s) below to class: %s", joinSimpleNames(classes), ancestorClass.getSimpleName());
        return randomFrom(classes, createObject);
    }

    public static <In> Sequence<In> randomSequenceFrom(Collection<In> collection) {
        return Sequence.first(randomFrom(collection)).next((it -> randomFrom(collection)));
    }
}
