package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public final class MenuBar extends JMenuBar {

    public final JFrame jFrame;
    public MenuBar(JFrame jFrame) {
        this.jFrame = jFrame;
        this.add(generateLookAndFeelMenu());
        this.add(generateTestMenu());
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        addMenuItem(lookAndFeelMenu, "Системная схема", (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            jFrame.invalidate();
        });
        addMenuItem(lookAndFeelMenu, "Универсальная схема", (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            jFrame.invalidate();
        });

        return lookAndFeelMenu;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

        addMenuItem(testMenu, "Сообщение в лог", (event) -> Logger.debug("Новая строка"));
        return testMenu;
    }

    private void addMenuItem(JMenu menu, String text, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text, KeyEvent.VK_S);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(jFrame);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            Logger.debug("Ошибка при загрузке `LookAndFeel`: className=" + className);
        }
    }
}
