package log;

/**
 * Подписчик на обновление состояния логов.
 */
public interface LogChangeListener
{
    /**
     * Уведомить подписчика об обновлении состояния логов.
     */
    void onLogChanged();
}
