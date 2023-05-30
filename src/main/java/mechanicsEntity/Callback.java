package mechanicsEntity;

import java.awt.*;

abstract public class Callback {

    public void onTargetWasMoved(GameEntity entity, GameEntity target, Point oldValue, Point newValue) {

    }
    public void onPositionChanging(GameEntity entity, GameEntity target) {}

    public void onPositionChanged(GameEntity entity) {}

    public void onDying(GameEntity entity) {}

    public void onDied(GameEntity entity) {}

    public void onLive(GameEntity entity) {}
}
