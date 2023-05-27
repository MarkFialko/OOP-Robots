package mechanicsEntity;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    LinkedList<SnakeEntity> m_snake;

    Snake(SnakeEntity snake) {
        m_snake = new LinkedList<>();
        m_snake.add(snake);
    }


    public void addEntity() {
        int x = Math.round((float)(getLast().getPosition().x + Math.cos(getLast().getDirection() + Math.PI) * 6));
        int y = Math.round((float)(getLast().getPosition().y + Math.sin(getLast().getDirection() + Math.PI) * 6));
        SnakeEntity snakeEntity = new SnakeEntity(
                new Point(x, y),
                false,
                getHead().getMaxPoint(),
                0.2
        );
        getLast().addListener(snakeEntity, PropertyNames.POSITION);
        if (getLength() > 1) {
            getHead().addListener(snakeEntity, PropertyNames.POSITION);
        }
        m_snake.add(snakeEntity);
    }

    public void deleteEntity() throws GameStopException {
        if (getLength() > 1) {
            m_snake.pollLast();
        } else {
            throw new GameStopException();
        }
    }

    public SnakeEntity getHead() {
        return m_snake.get(0);
    }

    public void move(Point p) {
        getHead().moveRobot(p);
    }

    public SnakeEntity getLast() {
        return m_snake.getLast();
    }

    public List<SnakeEntity> getTail() {
        int size = m_snake.size();
        if (size > 1) {
            return m_snake.subList(1, size);
        } else {
            return Collections.emptyList();
        }
    }

    public int getLength() {
        return m_snake.size();
    }
}
