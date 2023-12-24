package _misc.shipsnpiers;

import lombok.Getter;

import java.util.Arrays;

import static _misc.shipsnpiers.GotFreightType.FreightType.CLOTHES;
import static _misc.shipsnpiers.GotFreightType.FreightType.ELECTRONICS;
import static _misc.shipsnpiers.GotFreightType.FreightType.FRUITS;

@Getter
class Pier extends GotFreightType {

    private int loadoutSpeed;

    public Pier(FreightType freightType) {
        super(freightType);
        this.loadoutSpeed = Arrays.stream(FreightLoadoutSpeed.values())
            .filter(fls -> fls.getFreightType() == freightType)
            .findFirst()
            .get().loadoutSpeed;
    }

    @Getter
    enum FreightLoadoutSpeed {
        ELECTRONICS_LOADOUT(ELECTRONICS, 10),
        FRUIT_LOADOUT(FRUITS, 25),
        CLOTHES_LOADOUT(CLOTHES, 50);

        private FreightType freightType;
        private int loadoutSpeed;

        FreightLoadoutSpeed(FreightType freightType, int loadoutSpeed) {
            this.freightType = freightType;
            this.loadoutSpeed = loadoutSpeed;
        }
    }
}
