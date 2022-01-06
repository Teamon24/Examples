package patterns.refactoring.guru.behavioral.chain_of_responsibility.ex1;

/**
 * Базовый класс цепочки.
 */
public abstract class Middleware {
    private Middleware next;

    /**
     * Помогает строить цепь из объектов-проверок.
     */
    public Middleware linkWith(Middleware next) {
        this.next = next;
        return next;
    }

    /**
     * Запускает проверку в следующем объекте или завершает проверку, если мы в
     * последнем элементе цепи.
     */
    protected boolean nextProcess(String email, String password) {
        if (next == null) {
            return true;
        }
        return next.process(email, password);
    }

    /**
     * Подклассы реализуют в этом методе конкретные проверки.
     */
    public abstract boolean process(String email, String password);
}