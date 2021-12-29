package patterns.refactoring.guru.creational.factory.method.ex1;

/**
 * Диалог на элементах операционной системы.
 */
public class WindowsDialog extends Dialog {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}