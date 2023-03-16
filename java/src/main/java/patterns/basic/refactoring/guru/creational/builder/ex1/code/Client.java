package patterns.basic.refactoring.guru.creational.builder.ex1.code;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.lang.System.out;

/**
 * Демо-класс. Здесь всё сводится воедино.
 */
public class Client {

    public static void main(String[] args) {
        Director director = new Director();
        CarBuilder builder = new CarBuilder();
        CarManualBuilder manualBuilder = new CarManualBuilder();

        Stream.<BiConsumer<Director, Builder>>
        of(
            Director::constructSportsCar,
            Director::constructCityCar,
            Director::constructSportUtilityVehicle
        ).forEach(
            constructCar -> {
                constructCar(director, builder, constructCar);
                constructManual(director, manualBuilder, constructCar);
            }
        );
    }

    private static void constructCar(final Director director,
                                     final CarBuilder builder,
                                     final BiConsumer<Director, Builder> constructCar)
    {
        // Директор получает объект конкретного строителя от клиента
        // (приложения). Приложение само знает какой строитель использовать,
        // чтобы получить нужный продукт.
        constructCar.accept(director, builder);

        // Готовый продукт возвращает строитель, так как Директор чаще всего не
        // знает и не зависит от конкретных классов строителей и продуктов.
        Car car = builder.getResult();
        out.println("---------------------------------");
        out.println("Car built: " + car.getCarType());
    }

    private static void constructManual(final Director director,
                                        final CarManualBuilder manualBuilder,
                                        final BiConsumer<Director, Builder> constructCar)
    {
        constructCar.accept(director, manualBuilder);
        Manual carManual = manualBuilder.getResult();
        out.println("Car manual built:\n" + carManual.print());
    }
}