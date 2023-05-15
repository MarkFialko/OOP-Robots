package gui;

import statesLoader.*;
import localization.LocaleApplication;
import localization.Names;
import common.ComparingHelpers;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class InternalFrame extends JInternalFrame implements PropertyChangeListener, PreservingState {
    protected final static LocaleApplication st_locale = LocaleApplication.getInstance();
    private final Names m_title;

    protected InternalFrame(Names title,
                            boolean resizable,
                            boolean closable,
                            boolean maximizable,
                            boolean iconifiable) {
        super(title.getTitle(), resizable, closable, maximizable, iconifiable);
        st_locale.addLocaleChangeListener(this);
        InternalFrameAdapter a;
        m_title = title;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new Adapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                super.internalFrameClosed(e);
                closed();
            }

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                super.internalFrameOpened(e);
                opened();
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
    public State saveState() {
        return new InternalFrameState(this);
    }

    @Override
    public void loadState(State state) {
        if (state instanceof InternalFrameState) {
            InternalFrameState internalFrameState = (InternalFrameState) state;
            internalFrameState.applyState(this);
        }
    }

    protected void closed() {
        StatesLoader.getInstance().saveState(this);
    }

    protected void opened() {
        StatesLoader.getInstance().loadStateIfNecessary(this);
    }

    protected void onChangeLocale() {
        setTitle(m_title.getTitle());
    }

}
