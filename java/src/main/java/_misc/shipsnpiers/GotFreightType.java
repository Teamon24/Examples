package _misc.shipsnpiers;

import lombok.ToString;

@ToString
abstract class GotFreightType {

    enum FreightType {
        CLOTHES, FRUITS, ELECTRONICS
    }

    protected FreightType freightType;

    public FreightType getType() {
        return freightType;
    }

    public GotFreightType(FreightType freightType) {
        this.freightType = freightType;
    }
}


