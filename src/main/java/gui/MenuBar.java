package gui;

import localization.Localization;
import log.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;


public final class MenuBar extends JMenuBar {

    public final JFrame jFrame;

    public MenuBar(JFrame jFrame) {
        this.jFrame = jFrame;
        this.add(generateLookAndFeelMenu());
        this.add(generateTestMenu());
        this.add(generateExitMenu());
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Localization.getString("lookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                Localization.getString("lookAndFeelMenuDescription"));

        addMenuItem(lookAndFeelMenu, Localization.getString("systemLookAndFeel"), (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            jFrame.invalidate();
        });
        addMenuItem(lookAndFeelMenu, Localization.getString("crossPlatformLookAndFeel"), (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            jFrame.invalidate();
        });

        return lookAndFeelMenu;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu(Localization.getString("testMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                Localization.getString("testMenuDescription"));

        addMenuItem(testMenu, Localization.getString("testMenuItem"),
                (event) -> Logger.debug(Localization.getString("strNewLine")));
        return testMenu;
    }

    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu(Localization.getString("exitMenu"));
        exitMenu.setMnemonic(KeyEvent.VK_ESCAPE);
        exitMenu.getAccessibleContext().setAccessibleDescription(Localization.getString("exitMenuDescription"));
        addMenuItem(exitMenu, Localization.getString("exitMenuItem"),
                (event) -> jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING)));
        return exitMenu;
    }

    private void addMenuItem(JMenu menu, String text, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text, KeyEvent.VK_S);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(jFrame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Ошибка при загрузке `LookAndFeel`: className=" + className);
        }
    }
}
