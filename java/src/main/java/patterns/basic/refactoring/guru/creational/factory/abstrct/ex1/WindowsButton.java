package patterns.basic.refactoring.guru.creational.factory.abstrct.ex1;

import static java.lang.System.out;

/**
 * Все семейства продуктов имеют одни и те же вариации (MacOS/Windows).
 *
 * Это вариант кнопки под Windows.
 */
public class WindowsButton implements Button {

    @Override
    public void paint() {
        out.println("You have created WindowsButton.");
    }
}