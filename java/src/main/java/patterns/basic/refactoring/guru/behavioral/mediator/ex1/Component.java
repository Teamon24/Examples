package patterns.basic.refactoring.guru.behavioral.mediator.ex1;

/**
 * Общий интерфейс компонентов.
 */
public interface Component {
    void setMediator(Mediator mediator);

    default String name() {
        return Names.getBy(this.getClass());
    }
}