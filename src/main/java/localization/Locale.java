package localization;

public enum Locale {
    EN_LOCALE("lang_en"),
    RU_LOCALE("lang_ru");

    public final String name;

    Locale(String baseName)
    {
        name = baseName;
    }
}
