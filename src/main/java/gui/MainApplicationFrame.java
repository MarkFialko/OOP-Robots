package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.*;

import localization.LocaleApplication;
import statesLoader.*;
import localization.Names;
import log.Logger;

/**
 * Класс, представляющий собой главное окно приложения.
 */
public class MainApplicationFrame extends JFrame implements PreservingState
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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new Adapter(){
            @Override
            public void windowClosed(WindowEvent e)
            {
                super.windowClosed(e);
                for(var internalFrame : desktopPane.getAllFrames()) {
                    internalFrame.dispose();
                }
                closed();
                System.exit(0);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                opened();
            }
        });

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(new MenuBar(this));
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
        Logger.debug(Names.PROTOCOL_WORKS.getTitle());
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

    @Override
    public StateFilePath getPath() {
        return StateFilePath.MAIN_WINDOW;
    }

    @Override
    public State saveState() {
        return new WindowState(this);
    }

    @Override
    public void loadState(State state) {
        if (state instanceof WindowState) {
            WindowState windowState = (WindowState) state;
            windowState.applyState(this);
        }
    }

    private void closed() {
        StatesLoader.getInstance().saveState(this);
        StatesLoader.getInstance().saveState(LocaleApplication.getInstance());
    }

    private void opened() {
        StatesLoader.getInstance().loadStateIfNecessary(this);
    }
}
