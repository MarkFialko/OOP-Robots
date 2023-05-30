package mechanicsEntity;

import javax.swing.text.html.Option;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

public abstract class GameEntity implements PropertyChangeListener {
    private static final String ST_OLD_VALUE = "OLD_VALUE";
    private static final String ST_NEW_VALUE = "NEW_VALUE";

    protected PropertyChangeSupport m_propChangeDispatcher;
    protected Point m_position;
    protected Optional<Callback> m_callback = Optional.empty();

    GameEntity(Point point) {
        m_propChangeDispatcher = new PropertyChangeSupport(this);
        setPosition(point);
    }

    public void setCallback(Callback callback) {
        m_callback = Optional.ofNullable(callback);
    }

    public Point getPosition() {
        return m_position;
    }

    public void setPosition(Point point) {
        Point oldValue = m_position;
        m_position = point;
        reportListeners(PropertyNames.POSITION, oldValue, m_position);

//        m_callback.ifPresent(callback -> callback.onPositionChanged(this));
    }

    public void addListener(GameEntity gameEntity, PropertyNames property) {
        m_propChangeDispatcher.addPropertyChangeListener(property.name(), gameEntity);
    }

    @Override
    public final void propertyChange(PropertyChangeEvent evt) {
        GameEntity gameEntity = (GameEntity) evt.getSource();
        PropertyNames property = PropertyNames.valueOf(evt.getPropertyName());
        propertyChange(gameEntity, property, evt.getOldValue(), evt.getNewValue());
    }

    protected final void reportListeners(PropertyNames property, Object oldValue, Object newValue) {
        m_propChangeDispatcher.firePropertyChange(property.name(), oldValue, newValue);
    }

    double calculateDistance(Point p) {
        return Math.sqrt(Math.pow(p.x - m_position.x, 2) + Math.pow(p.y - m_position.y, 2));
    }

    abstract public void propertyChange(GameEntity gameEntity, PropertyNames property, Object oldValue, Object newValue);

    protected void changePosition(Point target) {
        double distance = calculateDistance(target);
        double angle = angleTo(target);
        float x = (float)(m_position.x + Math.cos(angle) * distance);
        float y = (float)(m_position.y + Math.sin(angle) * distance);
        setPosition(new Point(Math.round(x), Math.round(y)));
    }

    /**
     * Возвращает угол из отрезка [0, 2 * pi] - напраление на точку с координатами ({@code toX}, {@code toY})
     *
     * @return напраление
     */
    protected double angleTo(Point target) {
        double diffX = target.x - m_position.x;
        double diffY = target.y - m_position.y;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    /**
     * Возвращает угол из отрезка [0, 2 * pi]
     *
     * @param angle
     * @return
     */
    protected static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}
