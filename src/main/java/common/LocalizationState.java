package common;

import localization.Locale;
import localization.LocaleApplication;

import java.io.Serializable;

public class LocalizationState implements Serializable {

    private Locale m_locale;

    private LocalizationState(){}

    public LocalizationState(LocaleApplication localeApp){
        m_locale = localeApp.getCurrentLocale();
    }

    public void applyState() {
        LocaleApplication.getInstance().setLocale(m_locale);
    }

}
