package architecture.patterns.basic.refactoring.guru.behavioral.state.ex1;

/**
 * Демо-класс. Здесь всё сводится воедино.
 */
public class Client {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}