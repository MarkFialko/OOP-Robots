package gui;

import localization.Names;

import java.awt.BorderLayout;

import javax.swing.*;

/**
 * Класс, представляющий собой игровое окно.
 */
public class GameWindow extends InternalFrame {
    /**
     * Игровой визуализатор.
     */
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super(Names.GAME_FIELD, true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
