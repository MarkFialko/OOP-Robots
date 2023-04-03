package gui;

import localization.LocaleApplication;
import localization.Names;
import modules.ComparingHelpers;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class InternalFrame extends JInternalFrame implements PropertyChangeListener {
    protected final static LocaleApplication st_locale = LocaleApplication.getInstance();
    private final Names m_title;

    protected InternalFrame(Names title,
                            boolean resizable,
                            boolean closable,
                            boolean maximizable,
                            boolean iconifiable) {
        super(title.getTitle(), resizable, closable, maximizable, iconifiable);
        st_locale.addLocaleChangeListener(this);
        m_title = title;
        addClosingConfirmation();
    }

    protected final LocaleApplication getLocaleApp() {
        return st_locale;
    }

    private void addClosingConfirmation() {
        JInternalFrame internalFrame = this;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClosing(DialogBeforeClosing.showWarningMessage(internalFrame));
            }
        });
    }

    protected void handleClosing(int answer) {
        switch (answer) {
            case JOptionPane.YES_OPTION:
                dispose();
                break;
            case JOptionPane.NO_OPTION:
                break;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ComparingHelpers.areEqual(st_locale, evt.getSource())) {
            if (ComparingHelpers.areEqual(LocaleApplication.PROPERTY_LOCALE, evt.getPropertyName())) {
                onChangeLocale();
            }
        }
    }

    protected void onChangeLocale() {
        setTitle(m_title.getTitle());
    }

}
