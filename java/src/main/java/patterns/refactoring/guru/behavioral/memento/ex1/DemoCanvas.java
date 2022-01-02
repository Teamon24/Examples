package patterns.refactoring.guru.behavioral.memento.ex1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import static patterns.refactoring.guru.behavioral.memento.ex1.Adapters.colorizer;
import static patterns.refactoring.guru.behavioral.memento.ex1.Adapters.dragger;
import static patterns.refactoring.guru.behavioral.memento.ex1.Adapters.selector;
import static patterns.refactoring.guru.behavioral.memento.ex1.Adapters.undoRedoAdapter;

class DemoCanvas extends Canvas {
    private final Editor editor;
    private final JFrame frame;
    private static final int PADDING = 10;

    DemoCanvas(Editor editor) {
        this.editor = editor;
        this.frame = new JFrame();
        this.createFrame(this.frame);
        this.attachKeyboardListeners();
        this.attachMouseListeners();
        this.refresh();
    }

    private void createFrame(final JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel();
        Border padding = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING);
        contentPanel.setBorder(padding);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        frame.setContentPane(contentPanel);

        contentPanel.add(new JLabel("Select and drag to move."), BorderLayout.PAGE_END);
        contentPanel.add(new JLabel("Right click to change color."), BorderLayout.PAGE_END);
        contentPanel.add(new JLabel("Undo: Ctrl+Z, Redo: Ctrl+R"), BorderLayout.PAGE_END);
        contentPanel.add(this);
        frame.setVisible(true);
        contentPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void attachKeyboardListeners() {
        this.addKeyListener(undoRedoAdapter(editor));
    }

    private void attachMouseListeners() {
        Supplier<Object> repaint = () -> {
            repaint();
            return null;
        };

        this.addMouseListener(colorizer(editor, repaint));
        this.addMouseListener(selector(editor, repaint));
        MouseAdapter dragger = dragger(editor, repaint);
        this.addMouseListener(dragger);
        this.addMouseMotionListener(dragger);
    }


    public int getWidth() {
        return editor.getShapes().getX() + editor.getShapes().getWidth() + PADDING;
    }

    public int getHeight() {
        return editor.getShapes().getY() + editor.getShapes().getHeight() + PADDING;
    }

    void refresh() {
        this.setSize(getWidth(), getHeight());
        frame.pack();
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics graphics) {
        BufferedImage buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D ig2 = buffer.createGraphics();
        ig2.setBackground(Color.WHITE);
        ig2.clearRect(0, 0, this.getWidth(), this.getHeight());

        editor.getShapes().paint(buffer.getGraphics());

        graphics.drawImage(buffer, 0, 0, null);
    }
}