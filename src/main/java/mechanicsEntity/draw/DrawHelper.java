package mechanicsEntity.draw;

import mechanicsEntity.Food;
import mechanicsEntity.GameEntity;
import mechanicsEntity.Snake;
import mechanicsEntity.SnakeEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawHelper {
    public DrawHelper() {

    }

    public void draw(Graphics2D g2d, Snake snake) {
        SnakeEntity head = snake.getHead();
        drawRobot(g2d, head);
        for (SnakeEntity snakeEntity : snake.getTail()) {
            drawAndFillCircle(g2d, snakeEntity, Color.MAGENTA, 6);
        }
    }

    public void draw(Graphics2D g2d, Food food) {
        drawAndFillCircle(g2d, food, Color.GREEN, 5);
    }

    private void drawRobot(Graphics2D g, SnakeEntity snakeEntity)
    {
        int robotCenterX = snakeEntity.getPosition().x;
        int robotCenterY = snakeEntity.getPosition().y;
        double direction = snakeEntity.getDirection();
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }


    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawAndFillCircle(Graphics2D g, GameEntity gameEntity, Color color, int diam) {
        int x = gameEntity.getPosition().x;
        int y = gameEntity.getPosition().y;
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, diam, diam);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, diam, diam);
    }
}
