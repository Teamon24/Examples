package architecture.patterns.basic.refactoring.guru.creational.factory.abstrct.ex1;

import static java.lang.System.out;

/**
 * Все семейства продуктов имеют одинаковые вариации (MacOS/Windows).
 *
 * Вариация чекбокса под Windows.
 */
public class WindowsCheckbox implements Checkbox {

    @Override
    public void paint() {
        out.println("You have created WindowsCheckbox.");
    }
}