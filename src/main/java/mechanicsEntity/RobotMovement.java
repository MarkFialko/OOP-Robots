package mechanicsEntity;

import gui.Position;

/**
 * Класс, отвечающий за механику движения робота за целью.
 */
public class RobotMovement {
    private volatile Position<Double> m_robotPos;
    private volatile double m_robotDirection = 0;

    /**
     * Максимальная скорость движения.
     */
    private static final double maxVelocity = 0.1;

    /**
     * Максимальный угол изменения направления вектора движения.
     */
    private static final double maxAngularVelocity = 0.001;

    private static final double duration = 10;

    public RobotMovement(Position<Double> position) {
        m_robotPos = position;
    }

    public Position<Double> getPosition() {
        return m_robotPos;
    }

    public double getDirection() {
        return m_robotDirection;
    }

    /**
     * Вызывается визуализатором.
     *
     * @param targetPos позиция цели.
     */
    public void moveRobot(Position<Integer> targetPos, int height, int width) {
        double distance = distance(targetPos.getX(), targetPos.getY(),
                m_robotPos.getX(), m_robotPos.getY());
        if (distance < 0.5) {
            return;
        }
        double angleToTarget = angleTo(m_robotPos.getX(), m_robotPos.getY(), targetPos.getX(), targetPos.getY());
        double angularVelocity = 0;

        if (Math.abs(m_robotDirection - angleToTarget) < 10e-7) {
            angularVelocity = m_robotDirection;
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

        changePosition(maxVelocity, angularVelocity, height, width);
    }

    /**
     * @param velocity        скорость движения.
     * @param angularVelocity угол изменения направления вектора движения.
     */
    private void changePosition(double velocity, double angularVelocity, int height, int width) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        double newX = m_robotPos.getX() + velocity * duration * Math.cos(newDirection);
        if (width != 0)
            newX = applyLimits(newX, 0, width);
        double newY = m_robotPos.getY() + velocity * duration * Math.sin(newDirection);
        if (height != 0)
            newY = applyLimits(newY, 0, height);
        m_robotPos = new Position<>(newX, newY);
        m_robotDirection = newDirection;
    }

    /**
     * Рассчитать расстояние от точки с координатами ({@code x1}, {@code y1}) до точки ({@code x2}, {@code y2})
     *
     * @param x1 координата X первой точки.
     * @param y1 координата Y первой точки.
     * @param x2 координата X второй точки.
     * @param y2 координата Y второй точки.
     * @return расстояние между двумя точками.
     */
    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
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
    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
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

}
