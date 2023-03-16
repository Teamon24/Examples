package core.concurrency.thread.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomElements<E> {

    private final Random random = new Random();
    private final List<E> elements;

    public RandomElements(final List<E> elements) {
        this.elements = new ArrayList<>(elements);
    }

    public E getRandom() {
        int size = elements.size();
        if (size == 0) throw new RuntimeException("There are no elements in collection");
        int index = random.nextInt(size);
        E number = elements.get(index);
        elements.remove(number);
        return number;
    }
}
