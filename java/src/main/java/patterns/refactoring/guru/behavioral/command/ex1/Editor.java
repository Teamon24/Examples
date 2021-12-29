package patterns.refactoring.guru.behavioral.command.ex1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Editor {
    public JTextArea textField;
    public String clipboard;
    private CommandHistory history = new CommandHistory();

    public void init() {
        Editor editor = this;
        JFrame frame = new JFrame("Text editor (type & use buttons, Luke!)");
        JPanel content = new JPanel();

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        addTextField(content);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        createAndAddButton(buttons, "Ctrl+C", e -> executeCommand(new CopyCommand(editor)));
        createAndAddButton(buttons, "Ctrl+X", e -> executeCommand(new CutCommand(editor)));
        createAndAddButton(buttons, "Ctrl+V", e -> executeCommand(new PasteCommand(editor)));
        createAndAddButton(buttons, "Ctrl+Z", e -> undo());

        content.add(buttons);
        setUp(frame);
    }

    private void setUp(final JFrame frame) {
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addTextField(final JPanel jPanel) {
        textField = new JTextArea();
        textField.setLineWrap(true);
        jPanel.add(textField);
    }

    private void createAndAddButton(JPanel buttons,
                                    String buttonName,
                                    ActionListener actionListener)
    {
        JButton jButton = new JButton(buttonName);
        jButton.addActionListener(actionListener);
        buttons.add(jButton);
    }

    private void executeCommand(Command command) {
        if (command.execute()) {
            history.push(command);
        }
    }

    private void undo() {
        if (history.isEmpty()) return;

        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
}