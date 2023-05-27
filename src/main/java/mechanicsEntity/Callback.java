package mechanicsEntity;

import gui.GameWindow;
import gui.Position;

import java.awt.*;

abstract public class Callback {
    public void onPositionChanging(GameEntity entity, GameEntity target) {}

    public void onPositionChanged(GameEntity entity) {
        entity.reportListeners(PropertyNames.POSITION);
    }

    public void onDying(GameEntity entity) {}

    public void onDied(GameEntity entity) {
        entity.reportListeners(PropertyNames.DIE);
    }

    public void onLive(GameEntity entity) {
        entity.reportListeners(PropertyNames.LIVE);
    }
}
