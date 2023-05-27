package mechanicsEntity;

import java.awt.*;

public final class Food extends GameEntity {
    Food(Point point) {
        super(point);
    }

    @Override
    public void propertyChange(GameEntity gameEntity, PropertyNames property) {
        if (gameEntity instanceof SnakeEntity && property.equals(PropertyNames.POSITION)) {
            Point p = gameEntity.getPosition();
            if (calculateDistance(p) < 5) {
                m_callback.ifPresent(callback -> callback.onDied(this));
            }
        }
    }
}
