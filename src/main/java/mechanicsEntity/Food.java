package mechanicsEntity;

import java.awt.*;

public final class Food extends GameEntity {
    Food(Point point) {
        super(point);
    }

    @Override
    public void propertyChange(GameEntity gameEntity, PropertyNames property, Object oldValue, Object newValue) {
        if (gameEntity instanceof SnakeEntity && property.equals(PropertyNames.POSITION)) {
//            Point p = (Point) newValue;
            m_callback.ifPresent(callback -> callback.onTargetWasMoved(this, gameEntity, (Point) oldValue, (Point) newValue));
            if (gameEntity.calculateDistance(this.getPosition()) < 15) {
                m_callback.ifPresent(callback -> callback.onDied(this));
            }
        }
    }
}
