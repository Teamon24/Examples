package _misc.fizzbuzz;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>-eсли число делится на 3, выведите 'Fizz';
 * <p>-если число делится на 5, выведите 'Buzz';
 * <p>-если число делится и на 3 и на 5, выведите 'FizzBuzz'.
 */
public final class FizzBuzz {
    public final HashMap<Integer, String> map;
    public final List<Integer> divs;

    public FizzBuzz(HashMap<Integer, String> map) {
        this.map = map;
        divs = map.keySet().stream().collect(Collectors.toList());
    }

    public Collection<Integer> filter(Collection<Integer> numbers) {
        return numbers.stream()
            .map(n -> Pair.of(n, getName(n)))
            .filter(pair -> !pair.getRight().isBlank())
            .map(Pair::getLeft).collect(Collectors.toList());
    }

    public String getName(Integer number) {
        for (Integer div : divs) {
            if (number.equals(div)) {
                return map.get(div);
            }
        }

        StringBuilder temp = new StringBuilder();
        int n = number;
        for (int divCurr : divs) {
            if (n % divCurr == 0) {
                temp.append(map.get(divCurr));
                n = n / divCurr;
            }
        }

        return temp.toString();
    }
}
