package architecture.patterns.basic.refactoring.guru.behavioral.mediator.ex1;

import java.util.HashMap;

public class Names {

    private static final HashMap<Class, String> elementsAndNames = new HashMap<>();

    public static String getBy(Class elementClass) {
        String name = elementsAndNames.get(elementClass);
        if (name == null) throw new RuntimeException("There is no name for class: " + elementClass.getSimpleName());
        return name;
    }

    public static void init() {
        elementsAndNames.put(Editor.class, "Editor");
        elementsAndNames.put(DeleteButton.class, "DelButton");
        elementsAndNames.put(AddButton.class, "AddButton");
        elementsAndNames.put(Filter.class, "Filter");
        elementsAndNames.put(List.class, "List");
        elementsAndNames.put(SaveButton.class, "SaveButton");
        elementsAndNames.put(TextBox.class, "TextBox");
        elementsAndNames.put(Title.class, "Title");
    }
}
