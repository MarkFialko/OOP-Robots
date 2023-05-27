package mechanicsEntity;

import gui.Position;

import java.awt.*;
import java.util.LinkedList;

public class SnakeEntity extends GameEntity {

    private final Point m_maxPoint;

    private final boolean m_isHead;

    private volatile double m_robotDirection = 0;

    /**
     * Максимальная скорость движения.
     */
    private final double maxVelocity;

    /**
     * Максимальный угол изменения направления вектора движения.
     */
    private static final double maxAngularVelocity = 0.005;

    private static final double duration = 10;

    SnakeEntity(Point point, boolean isHead, Point maxPoint, double maxVelocity) {
        super(point);
        m_maxPoint = maxPoint;
        m_isHead = isHead;
        this.maxVelocity = maxVelocity;
    }

    public double getDirection() {
        return m_robotDirection;
    }

    @Override
    public void propertyChange(GameEntity gameEntity, PropertyNames property) {
        if (gameEntity instanceof SnakeEntity && property.equals(PropertyNames.POSITION)) {
            SnakeEntity snakeEntity = (SnakeEntity) gameEntity;
            Point p = snakeEntity.getPosition();
            if (snakeEntity.isHead() && calculateDistance(p) < 6) {
                snakeEntity.m_callback.ifPresent(callback -> callback.onDied(this));
            } else {
                m_callback.ifPresent(callback -> callback.onPositionChanging(this, gameEntity));
                moveRobot(p);
            }
        }
    }

    public void moveRobot(Point target) {
        double distance = calculateDistance(target) - 2;
        if (distance < 0) {
            return;
        }
        double angleToTarget = angleTo(m_position.x, m_position.y, target.x, target.y);
        double angularVelocity = 0;

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
        changePosition(angularVelocity);
    }

    /**
     * @param angularVelocity угол изменения направления вектора движения.
     */
    private void changePosition(double angularVelocity) {
        Point newPosition = new Point();
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        int newX = Math.round((float)(m_position.x + maxVelocity * duration * Math.cos(newDirection)));
        newPosition.x = applyLimits(newX, 0, m_maxPoint.x);
        int newY = Math.round((float)(m_position.y + maxVelocity * duration * Math.sin(newDirection)));
        newPosition.y = applyLimits(newY, 0, m_maxPoint.y);

        m_robotDirection = newDirection;
        setPosition(newPosition);
    }


    /**
     * Возвращает угол из отрезка [0, 2 * pi] - напраление на точку с координатами ({@code toX}, {@code toY})
     *
     * @param fromX координата X исходной точки.
     * @param fromY координата Y исходной точки.
     * @param toX   координата X точки-цели.
     * @param toY   координата Y точки-цели.
     * @return напраление
     */
    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
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

    /**
     * Возвращает угол из отрезка [0, 2 * pi]
     *
     * @param angle
     * @return
     */
    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    boolean isHead() {
        return m_isHead;
    }

    Point getMaxPoint() {
        return m_maxPoint;
    }


}
