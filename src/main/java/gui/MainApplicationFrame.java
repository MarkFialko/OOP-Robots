package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import log.Logger;

/**
 * Класс, представляющий собой главное окно приложения.
 */
public class MainApplicationFrame extends JFrame
{
    /**
     * Клиентская область фрейма, в которую вставляются дочерние компоненты.
     */
    private final JDesktopPane desktopPane = new JDesktopPane();

    /**
     * Создать окно приложения с отступом большого окна на 50 пикселей от каждого края экрана.
     */
    public MainApplicationFrame() {
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(new MenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Создать окно логов.
     * @return окно с логами.
     */
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    /**
     * Создать игорового окна.
     * @return окно с игрой.
     */
    protected GameWindow createGameWindow()
    {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        return gameWindow;
    }

    /**
     * Добавить окно к клиентскому фрейму и сделать окно видимым.
     * @param frame окно, которое необходимо вставить в фрейм.
     */
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
