package architecture.patterns.basic.refactoring.guru.creational.builder.ex1.code;

/**
 * Директор знает в какой последовательности заставлять работать строителя. Он
 * работает с ним через общий интерфейс Строителя. Из-за этого, он может не
 * знать какой конкретно продукт сейчас строится.
 */
public class Director {

    public void constructSportsCar(Builder builder) {
        builder
            .setCarType(CarType.SPORTS_CAR)
            .setSeats(2)
            .setEngine(new Engine(3.0, 0))
            .setTransmission(Transmission.SEMI_AUTOMATIC)
            .setTripComputer(new TripComputer())
            .setGPSNavigator(new GPSNavigator());
    }

    public void constructCityCar(Builder builder) {
        builder
            .setCarType(CarType.CITY_CAR)
            .setSeats(2)
            .setEngine(new Engine(1.2, 0))
            .setTransmission(Transmission.AUTOMATIC)
            .setTripComputer(new TripComputer())
            .setGPSNavigator(new GPSNavigator());
    }

    public void constructSportUtilityVehicle(Builder builder) {
        builder
            .setCarType(CarType.SPORT_UTILITY_VEHICLE)
            .setSeats(4)
            .setEngine(new Engine(2.5, 0))
            .setTransmission(Transmission.MANUAL)
            .setGPSNavigator(new GPSNavigator());
    }
}