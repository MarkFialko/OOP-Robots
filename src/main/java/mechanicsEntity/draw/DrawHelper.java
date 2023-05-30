package mechanicsEntity.draw;

import mechanicsEntity.Food;
import mechanicsEntity.GameEntity;
import mechanicsEntity.Snake;
import mechanicsEntity.SnakeEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawHelper {
    double ratioX = 1.0;
    double ratioY = 1.0;
    public DrawHelper() {

    }

    public void draw(Graphics2D g2d, Snake snake) {
        SnakeEntity head = snake.getHead();
        drawRobot(g2d, head);
        for (SnakeEntity snakeEntity : snake.getTail()) {
            drawAndFillCircle(g2d, snakeEntity, Color.MAGENTA, snakeEntity.getRadius() * 2);
        }
    }

    public void draw(Graphics2D g2d, Food food) {
        drawAndFillCircle(g2d, food, Color.GREEN, 5);
    }

    public void setRatio(double ratioX, double ratioY) {
        this.ratioX = ratioX;
        this.ratioY = ratioY;
    }

    private int getX(int x) {
        return Math.round((float)(x * ratioX));
    }

    private int getY(int y) {
        return Math.round((float)(y * ratioY));
    }

    private void drawRobot(Graphics2D g, SnakeEntity snakeEntity)
    {
        int robotCenterX = getX(snakeEntity.getPosition().x);
        int robotCenterY = getY(snakeEntity.getPosition().y);
        double direction = snakeEntity.getDirection();
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        int diam = snakeEntity.getRadius() * 2;
        fillOval(g, robotCenterX, robotCenterY, diam, diam);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, diam, diam);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + diam / 2, robotCenterY, diam / 4, diam / 4);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + diam / 2, robotCenterY, diam / 4, diam / 4);
    }


    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawAndFillCircle(Graphics2D g, GameEntity gameEntity, Color color, int diam) {
        int x = getX(gameEntity.getPosition().x);
        int y = getY(gameEntity.getPosition().y);
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(color);
        fillOval(g, x, y, diam, diam);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, diam, diam);
    }
}
