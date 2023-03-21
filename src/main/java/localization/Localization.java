package localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    static private final ResourceBundle rb;

    static {
        String baseName;
        if(Locale.getDefault().getLanguage().equals("ru")) {
            baseName = "lang_ru";
        } else {
            baseName = "lang_en";
        }
        rb = ResourceBundle.getBundle(baseName);
    }

    static public String getString(String key) {
        return rb.getString(key);
    }
}
