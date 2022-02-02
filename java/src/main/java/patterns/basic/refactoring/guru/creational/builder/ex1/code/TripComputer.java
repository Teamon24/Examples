package patterns.basic.refactoring.guru.creational.builder.ex1.code;

import static utils.PrintUtils.println;

/**
 * Одна из фишек автомобиля.
 */
public class TripComputer {

    private Car car;

    public void setCar(Car car) {
        this.car = car;
    }

    public void showFuelLevel() {
        println("Fuel level: " + this.car.getFuel());
    }

    public void showStatus() {
        if (this.car.getEngine().isStarted()) {
            println("Car is started");
        } else {
            println("Car isn't started");
        }
    }
}