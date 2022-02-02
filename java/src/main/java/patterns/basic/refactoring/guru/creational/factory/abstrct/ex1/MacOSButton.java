package patterns.basic.refactoring.guru.creational.factory.abstrct.ex1;

import static utils.PrintUtils.println;

/**
 * Все семейства продуктов имеют одни и те же вариации (MacOS/Windows).
 *
 * Это вариант кнопки под MacOS.
 */
public class MacOSButton implements Button {

    @Override
    public void paint() {
        println("You have created MacOSButton.");
    }
}