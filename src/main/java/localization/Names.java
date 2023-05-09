package localization;

public enum Names {
    WORK_PROTOCOL("workProtocol"),
    PROTOCOL_WORKS("protocolWorks"),
    GAME_FIELD("gameField"),
    LOOK_AND_FEEL_MENU("lookAndFeelMenu"),
    LOOK_AND_FEEL_MENU_DESCR("lookAndFeelMenuDescription"),
    SYSTEM_LOOK_AND_FEEL("systemLookAndFeel"),
    CROSS_PLATFORM_LOOK_AND_FEEL("crossPlatformLookAndFeel"),
    TEST_MENU("testMenu"),
    TEST_MENU_DESCR("testMenuDescription"),
    TEST_MENU_ITEM("testMenuItem"),
    STR_NEW_LINE("strNewLine"),
    EXIT_MENU("exitMenu"),
    EXIT_MENU_DESCR("exitMenuDescription"),
    EXIT_MENU_ITEM("exitMenuItem"),
    YES("yes"),
    NO("no"),
    CLOSE_DIALOG("closeDialog"),
    WARNING("warning"),
    STATE_DIALOG("stateDialog"),
    LANGUAGE_MENU("languageMenu"),
    RU_MENU_ITEM("ruMenuItem"),
    EN_MENU_ITEM("enMenuItem");

    private static final LocaleApplication st_localeApp = LocaleApplication.getInstance();

    public final String propertyName;

    Names(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getTitle()
    {
        return st_localeApp.getString(this);
    }
}
