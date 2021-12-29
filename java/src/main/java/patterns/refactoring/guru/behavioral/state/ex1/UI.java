package patterns.refactoring.guru.behavioral.state.ex1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UI {
    private Player player;
    private static JTextField textField = new JTextField();

    public UI(Player player) {
        this.player = player;
    }

    public void init() {
        JFrame frame = new JFrame("Test player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel context = new JPanel();
        context.setLayout(new BoxLayout(context, BoxLayout.Y_AXIS));

        frame.getContentPane().add(context);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        context.add(textField);
        context.add(buttons);

        initButtonsInfo().forEach(
            (buttonName, action) -> createButton(buttons, buttonName, action));

        setUp(frame);
    }

    private HashMap<String, ActionListener> initButtonsInfo() {
        HashMap<String, ActionListener> buttonNameAndAction = new HashMap<>();

        // Контекст заставляет состояние реагировать на пользовательский ввод
        // вместо себя. Реакция может быть разной в зависимости от того, какое
        // состояние сейчас активно.
        buttonNameAndAction.put("Play", e -> textField.setText(player.getState().onPlay()));
        buttonNameAndAction.put("Stop", e -> textField.setText(player.getState().onLock()));
        buttonNameAndAction.put("Next", e -> textField.setText(player.getState().onNext()));
        buttonNameAndAction.put("Prev", e -> textField.setText(player.getState().onPrevious()));

        return buttonNameAndAction;
    }

    private void createButton(final JPanel buttons,
                              final String buttonName,
                              final ActionListener actionForListener)
    {
        JButton jButton = new JButton(buttonName);
        jButton.addActionListener(actionForListener);
        buttons.add(jButton);
    }

    private void setUp(final JFrame frame) {
        frame.setVisible(true);
        frame.setSize(300, 100);
    }
}