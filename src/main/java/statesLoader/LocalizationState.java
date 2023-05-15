package statesLoader;

import localization.Locale;
import localization.LocaleApplication;

import java.io.Serializable;

public class LocalizationState implements State {

    private Locale m_locale;

    private LocalizationState(){}

    public LocalizationState(LocaleApplication localeApp){
        m_locale = localeApp.getCurrentLocale();
    }

    public void applyState(LocaleApplication localeApp) {
        localeApp.setLocale(m_locale);
    }

}
