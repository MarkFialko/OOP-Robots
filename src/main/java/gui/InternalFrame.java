package gui;

import common.StateFilePath;
import localization.LocaleApplication;
import localization.Names;
import common.ComparingHelpers;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class InternalFrame extends JInternalFrame implements PropertyChangeListener {
    protected final static LocaleApplication st_locale = LocaleApplication.getInstance();
    private final Names m_title;

    protected InternalFrame(Names title,
                            StateFilePath statePath,
                            boolean resizable,
                            boolean closable,
                            boolean maximizable,
                            boolean iconifiable) {
        super(title.getTitle(), resizable, closable, maximizable, iconifiable);
        st_locale.addLocaleChangeListener(this);
        InternalFrameAdapter a;
        m_title = title;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new Adapter(statePath) {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                super.internalFrameClosed(e);
                closed();
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

    protected void closed() {

    }

    protected void onChangeLocale() {
        setTitle(m_title.getTitle());
    }

}
