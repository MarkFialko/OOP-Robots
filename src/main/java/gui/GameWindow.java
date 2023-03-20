package gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * Класс, представляющий собой игровое окно.
 */
public class GameWindow extends JInternalFrame {
    /**
     * Игровой визуализатор.
     */
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        addClosingConfirmation();
        getContentPane().add(panel);
        pack();
    }

    private void addClosingConfirmation() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClosing();
            }
        });
    }

    private void handleClosing() {
        int answer = DialogBeforeClosing.showWarningMessage(this);
        switch (answer) {
            case JOptionPane.YES_OPTION:
                dispose();
                break;
            case JOptionPane.NO_OPTION:
                break;
        }
    }
}
