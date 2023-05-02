package gui;

import localization.LocaleApplication;
import localization.Names;
import common.ComparingHelpers;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class InternalFrame extends JInternalFrame implements PropertyChangeListener, ClosableComponent {
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
        ClosableComponent closable = this;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                ClosingDialog.confirm(closable);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ComparingHelpers.areEqual(st_locale, evt.getSource())) {
            if (ComparingHelpers.areEqual(LocaleApplication.PROPERTY_LOCALE, evt.getPropertyName())) {
                onChangeLocale();
            }
        }
    }

    @Override
    public void onClose() {
        dispose();
    }

    protected void onChangeLocale() {
        setTitle(m_title.getTitle());
    }

}
