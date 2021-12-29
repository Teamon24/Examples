package patterns.refactoring.guru.creational.factory.abstrct.ex1;

/**
 * Все семейства продуктов имеют одни и те же вариации (MacOS/Windows).
 *
 * Это вариант кнопки под MacOS.
 */
public class MacOSButton implements Button {

    @Override
    public void paint() {
        System.out.println("You have created MacOSButton.");
    }
}