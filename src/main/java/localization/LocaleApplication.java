package localization;

import common.ComparingHelpers;
import statesLoader.LocalizationState;
import statesLoader.PreservingState;
import statesLoader.State;
import statesLoader.StateFilePath;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ResourceBundle;

public class LocaleApplication implements PreservingState {
    public static final String PROPERTY_LOCALE = "LocaleApplication.m_locale";
    static private final LocaleApplication st_localization = new LocaleApplication();
    static private volatile ResourceBundle m_rb;
    private final PropertyChangeSupport m_propChangeDispatcher;
    private volatile Locale m_locale;

    private LocaleApplication()
    {
        updateLocaleAndRB(Locale.RU_LOCALE);
        m_propChangeDispatcher = new PropertyChangeSupport(this);
    }

    static public LocaleApplication getInstance()
    {
        return st_localization;
    }

    private synchronized void updateLocaleAndRB(Locale locale)
    {
        m_locale = locale;
        m_rb = ResourceBundle.getBundle(m_locale.name);
    }

    public String getString(Names propertyName) {
        return m_rb.getString(propertyName.propertyName);
    }

    public synchronized void setLocale(Locale newLocale)
    {
        if (!ComparingHelpers.areEqual(m_locale, newLocale))
        {
            Locale oldLocale = m_locale;
            updateLocaleAndRB(newLocale);
            m_propChangeDispatcher.firePropertyChange(PROPERTY_LOCALE, oldLocale, newLocale);
        }
    }

    public Locale getCurrentLocale() {
        return m_locale;
    }

    public void addLocaleChangeListener(PropertyChangeListener listener)
    {
        m_propChangeDispatcher.addPropertyChangeListener(PROPERTY_LOCALE, listener);
    }

    @Override
    public StateFilePath getPath() {
        return StateFilePath.LOCALIZATION;
    }

    @Override
    public State saveState() {
        return new LocalizationState(st_localization);
    }

    @Override
    public void loadState(State state) {
        if (state instanceof LocalizationState) {
            LocalizationState localizationState = (LocalizationState) state;
            localizationState.applyState(st_localization);
        }
    }
}
