package log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LogWindowSource {
    /**
     * Максимально число элементов в очереди активных сообщений лога.
     */
    private final int m_iQueueLength;
    public static final String PROPERTY_LOG = "LogWindowSource.m_messages";
    private final PropertyChangeSupport m_propChangeDispatcher;
    private final BlockingQueue<LogEntry> m_messages;
    private final ReadWriteLock lock;

    public LogWindowSource(int iQueueLength) {
        m_iQueueLength = iQueueLength;
        m_messages = new LinkedBlockingQueue<>(iQueueLength);
        lock = new ReentrantReadWriteLock();

        m_propChangeDispatcher = new PropertyChangeSupport(this);

    }

    /**
     * Зарегистрировать нового слушателя на событие изменения состояния логов.
     *
     * @param listener слушатель.
     */
    public void registerListener(PropertyChangeListener listener) {
        m_propChangeDispatcher.addPropertyChangeListener(PROPERTY_LOG, listener);
    }

    /**
     * Добавить лог.
     *
     * @param logLevel   уровень логирования.
     * @param strMessage сообщение лога.
     */
    public void append(LogLevel logLevel, String strMessage) {
        lock.writeLock().lock();
        try {
            LogEntry newEntry = new LogEntry(logLevel, strMessage);
            LogEntry oldEntry = null;
            while(!m_messages.offer(newEntry)) {
                oldEntry = m_messages.poll(); //without blocking
            }
            m_propChangeDispatcher.firePropertyChange(PROPERTY_LOG, oldEntry, newEntry);
        } finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * Возвращает размер списка логов.
     *
     * @return размер списка.
     */
    public int size() {
        int size;
        lock.readLock().lock();
        try {
            size = m_messages.size();
        } finally {
            lock.readLock().unlock();
        }
        return size;
    }

    /**
     * Получить итератор на {@code count} записей логов начиная с {@code startFrom} записи.
     *
     * @param startFrom первая запись
     * @param count     количество записей
     * @return итератор
     */
    public Iterable<LogEntry> range(int startFrom, int count) {
        lock.readLock().lock();
        List<LogEntry> resultArray;
        try {
            if (startFrom < 0 || startFrom >= m_messages.size()) {
                resultArray = Collections.emptyList();
            } else {
                int indexTo = Math.min(startFrom + count, size());
                resultArray = Arrays.asList(
                        Arrays.copyOfRange(m_messages.toArray(new LogEntry[0]), startFrom, indexTo));
            }
        } finally {
            lock.readLock().unlock();
        }
        return resultArray;
    }

    /**
     * Получить итератор на все записи логов.
     *
     * @return итератор.
     */
    public Iterable<LogEntry> all() {
        return range(0, m_iQueueLength);
    }
}
