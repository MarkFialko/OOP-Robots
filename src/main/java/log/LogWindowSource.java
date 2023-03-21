package log;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWindowSource {
    /**
     * Максимально число элементов в очереди активных сообщений лога.
     */
    private final int m_iQueueLength;

    /**
     * Потокобезопасная очередь активных сообщений лога.
     */
    private final BlockingQueue<LogEntry> m_messages;

    /**
     * Список всех подписчиков.
     */
    private final ConcurrentLinkedQueue<WeakReference<LogChangeListener>> m_listeners;

    /**
     * volatile очередь всех активных подписчиков.
     */
    private volatile ConcurrentLinkedQueue<WeakReference<LogChangeListener>> m_activeListeners;

    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new LinkedBlockingQueue<>(iQueueLength);
        m_listeners = new ConcurrentLinkedQueue<>();
        m_activeListeners = null;
    }

    /**
     * Зарегистрировать нового подписчика на событие изменения состояния логов.
     *
     * @param listener подписчик.
     */
    public void registerListener(LogChangeListener listener) {
        WeakReference<LogChangeListener> weakListener = new WeakReference<>(listener);
        m_listeners.add(weakListener);
        m_activeListeners = null;
    }

    /**
     * Отменить регистрацию подписчика на собятие изменения состояния логов.
     *
     * @param listener подписчик.
     */
    public void unregisterListener(LogChangeListener listener) {
        //TODO работает ли?
        WeakReference<LogChangeListener> weakListener = new WeakReference<>(listener);
        m_listeners.remove(weakListener);
        m_activeListeners = null;
    }

    /**
     * Добавить лог.
     *
     * @param logLevel   уровень логирования.
     * @param strMessage сообщение лога.
     */
    public void append(LogLevel logLevel, String strMessage) {
        LogEntry newEntry = new LogEntry(logLevel, strMessage);
        if (m_messages.size() == m_iQueueLength)
            m_messages.poll(); //returns head of queue or null, without blocking
        m_messages.add(newEntry);

        //TODO activeListeners ???
        ConcurrentLinkedQueue<WeakReference<LogChangeListener>> activeListeners = m_activeListeners;
        if (activeListeners == null) {
            activeListeners = m_listeners;
            m_activeListeners = activeListeners;
        }
        notifyAllAboutLogChanges(activeListeners);
    }

    /**
     * Уведомить всех подписчиков {@code listeners} об изменении логов.
     *
     * @param listeners подписчики.
     */
    public void notifyAllAboutLogChanges(ConcurrentLinkedQueue<WeakReference<LogChangeListener>> listeners) {
        assert listeners != null;
        for (WeakReference<LogChangeListener> listenerRef : listeners) {
            LogChangeListener listener = listenerRef.get();
            if (listener != null)
                listener.onLogChanged();
        }
    }

    /**
     * Возвращает размер списка логов.
     *
     * @return размер списка.
     */
    public int size() {
        return m_messages.size();
    }

    /**
     * Получить итератор на {@code count} записей логов начиная с {@code startFrom} записи.
     *
     * @param startFrom первая запись
     * @param count     количество записей
     * @return итератор
     */
    public Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= m_messages.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_messages.size());
        return Arrays.asList(Arrays.copyOfRange(m_messages.toArray(new LogEntry[0]), startFrom, indexTo));
    }

    /**
     * Получить итератор на все записи логов.
     *
     * @return итератор.
     */
    public Iterable<LogEntry> all() {
        return m_messages;
    }
}