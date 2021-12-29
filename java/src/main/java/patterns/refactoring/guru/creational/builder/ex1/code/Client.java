package patterns.refactoring.guru.creational.builder.ex1.code;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

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
            it ->
            {
                constructCar(builder, director, it);
                constructManual(manualBuilder, director, it);
            }
        );
    }

    private static void constructCar(final CarBuilder builder,
                                     final Director director,
                                     final BiConsumer<Director, Builder> constructCar)
    {
        // Директор получает объект конкретного строителя от клиента
        // (приложения). Приложение само знает какой строитель использовать,
        // чтобы получить нужный продукт.
        constructCar.accept(director, builder);

        // Готовый продукт возвращает строитель, так как Директор чаще всего не
        // знает и не зависит от конкретных классов строителей и продуктов.
        Car car = builder.getResult();
        System.out.println("---------------------------------");
        System.out.println("Car built: " + car.getCarType());


    }

    private static void constructManual(final CarManualBuilder manualBuilder,
                                        final Director director,
                                        final BiConsumer<Director, Builder> constructCar)
    {
        // Директор получает объект конкретного строителя от клиента
        // (приложения). Приложение само знает какой строитель использовать,
        // чтобы получить нужный продукт.
        constructCar.accept(director, manualBuilder);

        // Готовый продукт возвращает строитель, так как Директор чаще всего не
        // знает и не зависит от конкретных классов строителей и продуктов.
        Manual carManual = manualBuilder.getResult();
        System.out.println("Car manual built:\n" + carManual.print());


    }
}