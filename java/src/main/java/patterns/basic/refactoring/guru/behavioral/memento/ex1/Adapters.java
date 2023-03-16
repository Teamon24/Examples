package patterns.basic.refactoring.guru.behavioral.memento.ex1;

import patterns.basic.refactoring.guru.behavioral.memento.ex1.command.ColorCommand;
import patterns.basic.refactoring.guru.behavioral.memento.ex1.command.MoveCommand;
import patterns.basic.refactoring.guru.behavioral.memento.ex1.shape.Shape;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public class Adapters {

    public static KeyAdapter undoRedoAdapter(final Editor editor) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_Z:
                            editor.undo();
                            break;
                        case KeyEvent.VK_R:
                            editor.redo();
                            break;
                    }
                }
            }
        };
    }

    public static MouseAdapter dragger(final Editor editor,
                                       final Supplier<?> repaint) {
        return new MouseAdapter() {
            MoveCommand moveCommand;

            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != MouseEvent.BUTTON1_DOWN_MASK) {
                    return;
                }
                if (moveCommand == null) {
                    moveCommand = new MoveCommand(editor);
                    moveCommand.start(e.getX(), e.getY());
                }
                moveCommand.move(e.getX(), e.getY());
                repaint.get();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1 || moveCommand == null) {
                    return;
                }
                moveCommand.stop(e.getX(), e.getY());
                editor.execute(moveCommand);
                this.moveCommand = null;
                repaint.get();
            }
        };
    }

    public static MouseAdapter colorizer(final Editor editor,
                                         final Supplier<?> repaint) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    return;
                }
                patterns.basic.refactoring.guru.behavioral.memento.ex1.shape.Shape target = editor.getShapes().getChildAt(e.getX(), e.getY());
                if (target != null) {
                    ColorCommand colorCommand = randomColorCommand(editor);
                    editor.execute(colorCommand);
                    repaint.get();
                }
            }
        };
    }

    public static MouseAdapter selector(final Editor editor,
                                        final Supplier<?> repaint) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                Shape target = editor.getShapes().getChildAt(e.getX(), e.getY());
                boolean ctrl = (e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK;

                if (target == null) {
                    if (!ctrl) {
                        editor.getShapes().unSelect();
                    }
                } else {
                    if (ctrl) {
                        if (target.isSelected()) {
                            target.unSelect();
                        } else {
                            target.select();
                        }
                    } else {
                        if (!target.isSelected()) {
                            editor.getShapes().unSelect();
                        }
                        target.select();
                    }
                }
                repaint.get();
            }
        };
    }

    private static ColorCommand randomColorCommand(final Editor editor) {
        int rgb = (int) (Math.random() * 0x1000000);
        return new ColorCommand(editor, new Color(rgb));
    }
}
