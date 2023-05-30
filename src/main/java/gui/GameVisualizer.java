package gui;

import mechanicsEntity.Food;
import mechanicsEntity.Game;
import mechanicsEntity.draw.DrawHelper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * Класс, представляющий собой игровой визуализатор.
 */
public class GameVisualizer extends JPanel
{
    private final Timer m_timer;
    private final Game m_game;
    private final DrawHelper drawHelper = new DrawHelper();
    
    public GameVisualizer() 
    {
        super(true);
        setFocusable(true);
        m_game = new Game();

        m_timer = new Timer("events generator", true);;
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 100);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 50);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                m_game.setTarget(e);
            }
        });
        setDoubleBuffered(true);
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    private void updateStat() {
//        TODO statistic
    }
    
    protected void onModelUpdateEvent()
    {
        if(!m_game.stopped()) {
            m_game.move();
            updateStat();
        }
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawHelper.setRatio(1.0 * getSize().width / m_game.getWidth(), 1.0 * getSize().height / m_game.getHeight());
        drawHelper.draw(g2d, m_game.getUserSnake());
        drawHelper.draw(g2d, m_game.getGameSnake());
        for(Food food : m_game.getFoods()) {
            drawHelper.draw(g2d, food);
        }
    }
}
