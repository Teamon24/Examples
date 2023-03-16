package patterns.basic.refactoring.guru.creational.builder.ex1.code;

import static java.lang.System.out;

/**
 * Одна из фишек автомобиля.
 */
public class TripComputer {

    private Car car;

    public void setCar(Car car) {
        this.car = car;
    }

    public void showFuelLevel() {
        out.println("Fuel level: " + this.car.getFuel());
    }

    public void showStatus() {
        if (this.car.getEngine().isStarted()) {
            out.println("Car is started");
        } else {
            out.println("Car isn't started");
        }
    }
}