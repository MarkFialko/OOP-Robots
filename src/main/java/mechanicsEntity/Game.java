package mechanicsEntity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;

public class Game {
    private final Snake userSnake;
    private final Snake gameSnake;
    private final HashSet<Food> foods;
    private int score;

    private boolean m_stopped = false;

    private final int m_width = 400;
    private final int m_height = 400;

    private Point m_target;
    private final Random m_random = new Random();
    private boolean foodDied = false;
    private int enemyDelay = 0;

    public Game() {
        int radius = 10;
        SnakeEntity headUser = new SnakeEntity(
                new Point(m_width / 2, m_height / 2),
                radius,
                true,
                new Point(m_width, m_height),
                0.2);
        headUser.setCallback(createCallbackForUserHead());

        SnakeEntity gameHead = new SnakeEntity(
                new Point(20, 20),
                radius,
                true,
                new Point(m_width, m_height),
                0.1);
        gameHead.setCallback(createCallbackForGameHead());
        headUser.addListener(gameHead, PropertyNames.POSITION);

        userSnake = new Snake(headUser);
        gameSnake = new Snake(gameHead);

        foods = new HashSet<>();
        addFood();

    }

    private void addFood() {
        Food food = new Food(randomPoint());
        food.setCallback(createCallbackForFood());
        userSnake.getHead().addListener(food, PropertyNames.POSITION);
        foods.add(food);
    }

    Point randomPoint() {
        return new Point(m_random.nextInt(m_width), m_random.nextInt(m_height));
    }

    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }

    public void setTarget(KeyEvent e) {
        m_target = switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> new Point(userSnake.getHead().getPosition().x, 0);
            case KeyEvent.VK_A -> new Point(0, userSnake.getHead().getPosition().y);
            case KeyEvent.VK_S -> new Point(userSnake.getHead().getPosition().x, m_height);
            case KeyEvent.VK_D -> new Point(m_width, userSnake.getHead().getPosition().y);
            default -> m_target;
        };
    }

    public void move() {
        if (m_target != null && !m_stopped) {
            userSnake.move(m_target);
            if (userSnake.getHead().calculateDistance(gameSnake.getHead().getPosition()) < gameSnake.getHead().getRadius() * 2 && enemyDelay == 0) {
                makeDamage();
            }
            checks();
        }
        if (foodDied) {
            foodDied = false;
            score++;
            userSnake.addEntity();
            enemyDelay += 10;
            addFood();
        }
    }

    private void makeDamage() {
        try {
            userSnake.deleteEntity();
        } catch (GameStopException e) {
            m_stopped = true;
        }
        enemyDelay += 30;
    }

    private void checks() {
        boolean first = true;
        for(SnakeEntity snakeEntity : userSnake.getTail()) {
            if (first) {
                first = false;
                continue;
            }
            if (userSnake.getHead().calculateDistance(snakeEntity.getPosition()) < snakeEntity.getRadius() * 2) {
                m_stopped = true;
            }
        }
    }

    public Snake getUserSnake() {
        return userSnake;
    }

    public Snake getGameSnake() {
        return gameSnake;
    }

    public HashSet<Food> getFoods() {
        return foods;
    }

    private Callback createCallbackForUserHead() {
        return new Callback() {
            @Override
            public void onPositionChanged(GameEntity entity) {
                SnakeEntity snakeEntity = (SnakeEntity) entity;
                if (snakeEntity.calculateDistance(m_target) < snakeEntity.getRadius()) {
                    m_stopped = true;
                }
            }
        };
    }

    public boolean stopped() {
        return m_stopped;
    }

    private Callback createCallbackForGameHead() {
        return new Callback() {
            @Override
            public void onTargetWasMoved(GameEntity entity, GameEntity target, Point oldValue, Point newValue) {
                if (enemyDelay == 0) {
                    SnakeEntity snakeEntity = (SnakeEntity) entity;
                    snakeEntity.moveRobot(newValue);
                } else {
                    enemyDelay--;
                }

            }
        };
    }

    private Callback createCallbackForFood() {
        return new Callback() {
            @Override
            public void onDied(GameEntity entity) {
                if (foods.contains((Food) entity)) {
                    foodDied = true;
                    foods.remove((Food) entity);
                }
            }
        };
    }


}
