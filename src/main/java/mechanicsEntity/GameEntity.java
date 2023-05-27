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
        m_position = point;
        m_callback.ifPresent(callback -> callback.onPositionChanged(this));
    }

    public void addListener(GameEntity gameEntity, PropertyNames property) {
        m_propChangeDispatcher.addPropertyChangeListener(property.name(), gameEntity);
    }

    @Override
    public final void propertyChange(PropertyChangeEvent evt) {
        GameEntity gameEntity = (GameEntity) evt.getSource();
        PropertyNames property = PropertyNames.valueOf(evt.getPropertyName());
        propertyChange(gameEntity, property);
    }

    protected final void reportListeners(PropertyNames property) {
        m_propChangeDispatcher.firePropertyChange(property.name(), ST_OLD_VALUE, ST_NEW_VALUE);
    }

    double calculateDistance(Point p) {
        return Math.sqrt(Math.pow(p.x - m_position.x, 2) + Math.pow(p.y - m_position.y, 2));
    }

    abstract public void propertyChange(GameEntity gameEntity, PropertyNames property);
}
