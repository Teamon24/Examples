package patterns.basic.refactoring.guru.behavioral.memento.ex1.command;

import patterns.basic.refactoring.guru.behavioral.memento.ex1.Editor;
import patterns.basic.refactoring.guru.behavioral.memento.ex1.shape.Shape;

import java.awt.*;

public class ColorCommand implements Command {
    private Editor editor;
    private Color color;

    public ColorCommand(Editor editor, Color color) {
        this.editor = editor;
        this.color = color;
    }

    @Override
    public String getName() {
        return "Colorize: " + color.toString();
    }

    @Override
    public void execute() {
        for (Shape child : editor.getShapes().getSelected()) {
            child.setColor(color);
        }
    }
}