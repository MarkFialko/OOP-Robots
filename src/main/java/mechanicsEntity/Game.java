package mechanicsEntity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;

public class Game {
    private final Snake userSnake;
    private final Snake gameSnake;
    private HashSet<Food> foods;
    private int score;

    private boolean m_stopped = false;

    private final int m_width = 400;
    private final int m_height = 400;

    private Point m_target;
    private Random m_random = new Random();

    public Game() {
        SnakeEntity headUser = new SnakeEntity(
                new Point(m_width / 2, m_height / 2),
                true,
                new Point(m_width, m_height),
                0.2);
        headUser.setCallback(createCallbackForUserHead());

        SnakeEntity gameHead = new SnakeEntity(
                new Point(20, 20),
                true,
                new Point(m_width, m_height),
                0.1);
        gameHead.setCallback(createCallbackForGameHead());
        headUser.addListener(gameHead, PropertyNames.POSITION);

        foods = new HashSet<>();
        addFood();
        userSnake = new Snake(headUser);
        gameSnake = new Snake(gameHead);
    }

    private void addFood() {
        Food food = new Food(randomPoint());
        food.setCallback(createCallbackForFood());
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
        System.out.println();
    }

    public void move() {
        if (m_target != null) {
            userSnake.move(m_target);
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
            public void onPositionChanging(GameEntity entity, GameEntity target) {
                super.onPositionChanging(entity, target);
            }

            @Override
            public void onPositionChanged(GameEntity entity) {
                super.onPositionChanged(entity);
            }

            @Override
            public void onDying(GameEntity entity) {
                try {
                    userSnake.deleteEntity();
                } catch (GameStopException e) {
                    m_stopped = true;
                }
                super.onDying(entity);
            }
        };
    }

    public boolean stopped() {
        return m_stopped;
    }

    private Callback createCallbackForGameHead() {
        return new Callback() {
            @Override
            public void onPositionChanging(GameEntity entity, GameEntity target) {
                super.onPositionChanging(entity, target);
            }

            @Override
            public void onPositionChanged(GameEntity entity) {
                super.onPositionChanged(entity);
            }

            @Override
            public void onDying(GameEntity entity) {
                super.onDying(entity);
            }

            @Override
            public void onDied(GameEntity entity) {
                super.onDied(entity);
            }
        };
    }

    private Callback createCallbackForSnakeEntity() {
        return new Callback() {
            @Override
            public void onPositionChanging(GameEntity entity, GameEntity target) {
                super.onPositionChanging(entity, target);
            }

            @Override
            public void onPositionChanged(GameEntity entity) {
                super.onPositionChanged(entity);
            }

            @Override
            public void onDying(GameEntity entity) {
                super.onDying(entity);
            }

            @Override
            public void onDied(GameEntity entity) {
                super.onDied(entity);
            }
        };
    }

    private Callback createCallbackForFood() {
        return new Callback() {
            @Override
            public void onDied(GameEntity entity) {
//                super.onDied(entity);
                score += 1;
                foods.remove((Food)entity);
                userSnake.addEntity();
                addFood();
                System.out.println(score);
            }
        };
    }


}
