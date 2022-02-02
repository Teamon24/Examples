package patterns.basic.refactoring.guru.creational.factory.abstrct.ex1;

import static utils.PrintUtils.println;

/**
 * Все семейства продуктов имеют одинаковые вариации (MacOS/Windows).
 *
 * Вариация чекбокса под MacOS.
 */
public class MacOSCheckbox implements Checkbox {

    @Override
    public void paint() {
        println("You have created MacOSCheckbox.");
    }
}