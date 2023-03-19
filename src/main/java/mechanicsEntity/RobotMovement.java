package mechanicsEntity;

import gui.Position;

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

    public RobotMovement(Position<Double> position)
    {
        m_robotPos = position;
    }

    public Position<Double> getPosition()
    {
        return m_robotPos;
    }

    public double getDirection()
    {
        return m_robotDirection;
    }

    /**
     * Вызывается визуализатором.
     * @param targetPos позиция цели.
     */
    public void moveRobot(Position<Integer> targetPos)
    {
        double distance = distance(targetPos.getX(), targetPos.getY(),
                m_robotPos.getX(), m_robotPos.getY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPos.getX(), m_robotPos.getY(), targetPos.getX(), targetPos.getY());
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        changePosition(velocity, angularVelocity, 10);
    }

    /**
     *
     * @param velocity скорость движения.
     * @param angularVelocity угол изменения направления вектора движения.
     * @param duration
     */
    private void changePosition(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPos.getX() + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPos.getX()+ velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPos.getY() - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPos.getY() + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPos = new Position<>(newX, newY);
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;

    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

}
