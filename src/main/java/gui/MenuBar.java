package gui;

import localization.Locale;
import localization.LocaleApplication;
import localization.Names;
import log.Logger;
import common.ComparingHelpers;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public final class MenuBar extends JMenuBar implements PropertyChangeListener {

    private final static LocaleApplication st_locale = LocaleApplication.getInstance();
    public final JFrame m_jFrame;
    private final JMenu m_lookAndFeelMenu;
    private final JMenu m_testMenu;
    private final JMenu m_localeMenu;
    private final JMenu m_exitMenu;


    public MenuBar(JFrame jFrame) {
        st_locale.addLocaleChangeListener(this);
        this.m_jFrame = jFrame;
        m_lookAndFeelMenu = generateLookAndFeelMenu();
        m_testMenu = generateTestMenu();
        m_localeMenu = generateLocaleMenu();
        m_exitMenu = generateExitMenu();
        this.add(m_lookAndFeelMenu);
        this.add(m_testMenu);
        this.add(m_localeMenu);
        this.add(m_exitMenu);
    }

    private JMenu generateLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Names.LOOK_AND_FEEL_MENU.getTitle());
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(Names.LOOK_AND_FEEL_MENU_DESCR.getTitle());

        addMenuItem(lookAndFeelMenu, Names.SYSTEM_LOOK_AND_FEEL.getTitle(), KeyEvent.VK_S, (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            m_jFrame.invalidate();
        });
        addMenuItem(lookAndFeelMenu, Names.CROSS_PLATFORM_LOOK_AND_FEEL.getTitle(), KeyEvent.VK_S, (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            m_jFrame.invalidate();
        });

        return lookAndFeelMenu;
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu(Names.TEST_MENU.getTitle());
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(Names.TEST_MENU_DESCR.getTitle());

        addMenuItem(testMenu, Names.TEST_MENU_ITEM.getTitle(), KeyEvent.VK_S,
                (event) -> Logger.debug(Names.STR_NEW_LINE.getTitle()));
        return testMenu;
    }

    private JMenu generateExitMenu() {
        JMenu exitMenu = new JMenu(Names.EXIT_MENU.getTitle());
        exitMenu.setMnemonic(KeyEvent.VK_ESCAPE);
        exitMenu.getAccessibleContext().setAccessibleDescription(Names.EXIT_MENU_DESCR.getTitle());
        addMenuItem(exitMenu, Names.EXIT_MENU_ITEM.getTitle(), KeyEvent.VK_S,
                (event) -> m_jFrame.dispatchEvent(new WindowEvent(m_jFrame, WindowEvent.WINDOW_CLOSING)));
        return exitMenu;
    }

    private JMenu generateLocaleMenu() {
        JMenu localeMenu = new JMenu(Names.LANGUAGE_MENU.getTitle());
        localeMenu.setMnemonic(KeyEvent.VK_L);
        addMenuItem(localeMenu, Names.EN_MENU_ITEM.getTitle(), KeyEvent.VK_E,
                (event) -> st_locale.setLocale(Locale.EN_LOCALE));
        addMenuItem(localeMenu, Names.RU_MENU_ITEM.getTitle(), KeyEvent.VK_R,
                (event) -> st_locale.setLocale(Locale.RU_LOCALE));

        return localeMenu;
    }

    private void addMenuItem(JMenu menu, String text, int keyEvent, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text, keyEvent);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(m_jFrame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Ошибка при загрузке `LookAndFeel`: className=" + className);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ComparingHelpers.areEqual(st_locale, evt.getSource())) {
            if (ComparingHelpers.areEqual(LocaleApplication.PROPERTY_LOCALE, evt.getPropertyName())) {
                renameTitles();
            }
        }
    }

    private void renameTitles() {
        m_lookAndFeelMenu.setText(Names.LOOK_AND_FEEL_MENU.getTitle());
        m_lookAndFeelMenu.getItem(0).setText(Names.SYSTEM_LOOK_AND_FEEL.getTitle());
        m_lookAndFeelMenu.getItem(1).setText(Names.CROSS_PLATFORM_LOOK_AND_FEEL.getTitle());

        m_testMenu.setText(Names.TEST_MENU.getTitle());
        m_testMenu.getAccessibleContext().setAccessibleDescription(Names.TEST_MENU_DESCR.getTitle());
        m_testMenu.getItem(0).setText(Names.TEST_MENU_ITEM.getTitle());

        m_localeMenu.setText(Names.LANGUAGE_MENU.getTitle());
        m_localeMenu.getItem(0).setText(Names.EN_MENU_ITEM.getTitle());
        m_localeMenu.getItem(1).setText(Names.RU_MENU_ITEM.getTitle());

        m_exitMenu.setText(Names.EXIT_MENU.getTitle());
        m_exitMenu.getItem(0).setText(Names.EXIT_MENU.getTitle());
    }
}
