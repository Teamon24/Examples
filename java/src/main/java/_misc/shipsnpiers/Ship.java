package _misc.shipsnpiers;

import lombok.Getter;

@Getter
class Ship extends GotFreightType {

    private final int capacity;

    Ship(int capacity, FreightType freightType) {
        super(freightType);
        this.capacity = capacity;
    }


    @Override
    public String toString() {
        return String.format(
            "Ship(%s, capacity=%s)", this.freightType, this.capacity);
    }
}
