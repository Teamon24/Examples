package architecture.patterns.basic.refactoring.guru.creational.factory.abstrct.ex1;

/**
 * Демо-класс. Здесь всё сводится воедино.
 */
public class Client {

    public static void main(String[] args) {
        Application app = configureApplication();
        app.paint();
    }

    /**
     * Приложение выбирает тип и создаёт конкретные фабрики динамически исходя
     * из конфигурации или окружения.
     */
    private static Application configureApplication() {
        String osName = System.getProperty("os.name").toLowerCase();
        GUIFactory factory = getGuiFactory(osName);
        Application app = new Application(factory);
        return app;
    }

    private static GUIFactory getGuiFactory(final String osName) {
        GUIFactory factory;
        if (osName.contains("mac")) {
            factory = new MacOSFactory();
        } else {
            factory = new WindowsFactory();
        }
        return factory;
    }
}