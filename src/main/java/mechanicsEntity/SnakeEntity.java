package mechanicsEntity;

import gui.Position;

import java.awt.*;
import java.util.LinkedList;

public class SnakeEntity extends GameEntity {

    private final Point m_maxPoint;

    private final int m_radius;

    private final boolean m_isHead;

    private volatile double m_robotDirection = 0;

    /**
     * Максимальная скорость движения.
     */
    private final double maxVelocity;

    /**
     * Максимальный угол изменения направления вектора движения.
     */
    private double maxAngularVelocity;

    private static final double duration = 10;

    SnakeEntity(Point point, int radius, boolean isHead, Point maxPoint, double maxVelocity) {
        super(point);
        m_maxPoint = maxPoint;
        m_radius = radius;
        m_isHead = isHead;
        this.maxVelocity = maxVelocity;
        this.maxAngularVelocity = 0.005;
    }

    SnakeEntity(Point point, int radius, boolean isHead, Point maxPoint, double maxVelocity, double direction) {
        this(point, radius, isHead, maxPoint, maxVelocity);
        m_robotDirection = direction;
    }

    SnakeEntity(Point point, int radius, boolean isHead, Point maxPoint, double maxVelocity, double direction, double maxAngularVelocity) {
        this(point, radius, isHead, maxPoint, maxVelocity, direction);
        this.maxAngularVelocity = maxAngularVelocity;
    }

    public int getRadius() {
        return m_radius;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

    public double getDirection() {
        return m_robotDirection;
    }

    @Override
    public void propertyChange(GameEntity gameEntity, PropertyNames property, Object oldValue, Object newValue) {
        if (gameEntity instanceof SnakeEntity && property.equals(PropertyNames.POSITION)) {
//            SnakeEntity snakeEntity = (SnakeEntity) gameEntity;
//            Point p = snakeEntity.getPosition();
            m_callback.ifPresent(callback -> callback.onTargetWasMoved(this, gameEntity, (Point)oldValue, (Point)newValue));
        }
    }

    public void moveRobot(Point target) {
        if (maxAngularVelocity == 0) {
            changePosition(target);
        }
        double distance = calculateDistance(target);
//        if (distance < 5) {
//            return;
//        }
        double angleToTarget = angleTo(target);
        double angularVelocity;

        if (Math.abs(m_robotDirection - angleToTarget) < 10e-7) {
            angularVelocity = 0;
        } else if (m_robotDirection >= Math.PI) {
            if (m_robotDirection - Math.PI < angleToTarget && angleToTarget < m_robotDirection)
                angularVelocity = -maxAngularVelocity;
            else
                angularVelocity = maxAngularVelocity;
        } else {
            if (m_robotDirection < angleToTarget && angleToTarget < m_robotDirection + Math.PI)
                angularVelocity = maxAngularVelocity;
            else
                angularVelocity = -maxAngularVelocity;
        }
        changePosition(angularVelocity, distance);
    }

    /**
     * @param angularVelocity угол изменения направления вектора движения.
     */
    private void changePosition(double angularVelocity, double distance) {
        Point newPosition = new Point();
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        int newX = Math.round((float)(m_position.x + Math.min(maxVelocity * duration, distance) * Math.cos(newDirection)));
        newPosition.x = applyLimits(newX, 0, m_maxPoint.x);
        int newY = Math.round((float)(m_position.y + Math.min(maxVelocity * duration, distance) * Math.sin(newDirection)));
        newPosition.y = applyLimits(newY, 0, m_maxPoint.y);

        m_robotDirection = newDirection;
        setPosition(newPosition);
    }


    /**
     * @param value значение
     * @param min   левая граница
     * @param max   правая граница
     * @return {@code value} - если {@code value} лежит между {@code min} и {@code max},
     * иначе возвращает ближайшую границу.
     */
    private static int applyLimits(int value, int min, int max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }



    boolean isHead() {
        return m_isHead;
    }

    Point getMaxPoint() {
        return m_maxPoint;
    }


}
