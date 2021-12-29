package patterns.others.behavioral.chain_of_responsibility;

import java.util.LinkedHashMap;

public class Currency extends LinkedHashMap<Integer, Integer> {
    private Integer remainder;

    public Currency(final Integer remainder) {
        this.remainder = remainder;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(final Integer remainder) {
        this.remainder = remainder;
    }
}
